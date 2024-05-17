import java.util.Collections;
import java.util.List;

/**
 *
 * PodSecurityProfilesST provides tests for Pod Security profiles. In short, Pod security profiles are a mechanism used
 * in Pods or containers, which may prohibit some set of operations (e.g., running only as a non-root user, allowing
 * only some Volume types etc.).
 *
 * Test cases are design to verify common behaviour of Pod Security profiles. Specifically, (i.) we check if containers such
 * as Kafka, ZooKeeper, Entity Operator, KafkaBridge has properly set .securityContext (ii.) then we check if these
 * resources working and are stable with exchanging messages.
 */
@Tag(REGRESSION)
@Tag(POD_SECURITY_PROFILES_RESTRICTED)
public class PodSecurityProfilesST extends AbstractST {

    private static final Logger LOGGER = LogManager.getLogger(PodSecurityProfilesST.class);

    @Tag(ACCEPTANCE)
    @KindIPv6NotSupported("Kafka Connect Build using the Kaniko builder is not available under the restricted security profile")
    @ParallelNamespaceTest
    @RequiredMinKubeOrOcpBasedKubeVersion(kubeVersion = 1.23, ocpBasedKubeVersion = 1.24)
    void testOperandsWithRestrictedSecurityProfile() {

        final TestStorage testStorage = new TestStorage(ResourceManager.getTestContext());

        final String mm1TargetClusterName = testStorage.getTargetClusterName() + "-mm1";
        final String mm2TargetClusterName = testStorage.getTargetClusterName() + "-mm2";
        final String mm2SourceMirroredTopicName = testStorage.getClusterName() + "." + testStorage.getTopicName();

        // Label particular Namespace with pod-security.kubernetes.io/enforce: restricted
        NamespaceManager.labelNamespace(testStorage.getNamespaceName(),
            Collections.singletonMap("pod-security.kubernetes.io/enforce", "restricted"));

        // 1 source Kafka Cluster, 2 target Kafka Cluster, 1 for MM1 and MM2 each having different target Kafka Cluster,

        LOGGER.info("Deploy Kafka Clusters resources");
        resourceManager.createResourceWithWait(
            NodePoolsConverter.convertNodePoolsIfNeeded(
                KafkaNodePoolTemplates.brokerPoolPersistentStorage(testStorage.getNamespaceName(), testStorage.getBrokerPoolName(), testStorage.getClusterName(), 1).build(),
                KafkaNodePoolTemplates.controllerPoolPersistentStorage(testStorage.getNamespaceName(), testStorage.getControllerPoolName(), testStorage.getClusterName(), 1).build(),

                KafkaNodePoolTemplates.brokerPoolPersistentStorage(testStorage.getNamespaceName(), KafkaNodePoolResource.getBrokerPoolName(mm1TargetClusterName), mm1TargetClusterName, 1).build(),
                KafkaNodePoolTemplates.controllerPoolPersistentStorage(testStorage.getNamespaceName(), KafkaNodePoolResource.getControllerPoolName(mm1TargetClusterName), mm1TargetClusterName, 1).build(),

                KafkaNodePoolTemplates.brokerPoolPersistentStorage(testStorage.getNamespaceName(), KafkaNodePoolResource.getBrokerPoolName(mm2TargetClusterName), mm2TargetClusterName, 1).build(),
                KafkaNodePoolTemplates.controllerPoolPersistentStorage(testStorage.getNamespaceName(), KafkaNodePoolResource.getControllerPoolName(mm2TargetClusterName), mm2TargetClusterName, 1).build()
            )
        );
        resourceManager.createResourceWithWait(
            KafkaTemplates.kafkaPersistent(testStorage.getClusterName(), 1).build(),
            KafkaTemplates.kafkaPersistent(mm1TargetClusterName, 1).build(),
            KafkaTemplates.kafkaPersistent(mm2TargetClusterName, 1).build(),
            KafkaTopicTemplates.topic(testStorage).build()
        );

        // Kafka Bridge and KafkaConnect use main Kafka Cluster (one serving as source for MM1 and MM2)
        // MM1 and MM2 shares source Kafka Cluster and each have their own target kafka cluster

        LOGGER.info("Deploy all additional operands: MM1, MM2, Bridge, KafkaConnect");
        resourceManager.createResourceWithWait(
            KafkaConnectTemplates.kafkaConnectWithFilePlugin(testStorage.getClusterName(), testStorage.getNamespaceName(), 1)
                .editMetadata()
                .addToAnnotations(Annotations.STRIMZI_IO_USE_CONNECTOR_RESOURCES, "true")
                .endMetadata()
                .editSpec()
                .addToConfig("key.converter.schemas.enable", false)
                .addToConfig("value.converter.schemas.enable", false)
                .addToConfig("key.converter", "org.apache.kafka.connect.storage.StringConverter")
                .addToConfig("value.converter", "org.apache.kafka.connect.storage.StringConverter")
                .endSpec()
                .build(),
            KafkaBridgeTemplates.kafkaBridge(testStorage.getClusterName(), KafkaResources.plainBootstrapAddress(testStorage.getClusterName()), 1).build(),
            KafkaMirrorMakerTemplates.kafkaMirrorMaker(testStorage.getClusterName() + "-mm1", testStorage.getClusterName(), mm1TargetClusterName, ClientUtils.generateRandomConsumerGroup(), 1, false)
                .build(),
            KafkaMirrorMaker2Templates.kafkaMirrorMaker2(testStorage.getClusterName() + "-mm2", mm2TargetClusterName, testStorage.getClusterName(), 1, false)
                .editSpec()
                .editFirstMirror()
                .editSourceConnector()
                .addToConfig("refresh.topics.interval.seconds", "1")
                .endSourceConnector()
                .endMirror()
                .endSpec()
                .build());

        LOGGER.info("Deploy File Sink Kafka Connector: {}/{}", testStorage.getNamespaceName(), testStorage.getClusterName());
        resourceManager.createResourceWithWait(KafkaConnectorTemplates.kafkaConnector(testStorage.getClusterName())
            .editSpec()
            .withClassName("org.apache.kafka.connect.file.FileStreamSinkConnector")
            .addToConfig("topics", testStorage.getTopicName())
            .addToConfig("file", TestConstants.DEFAULT_SINK_FILE_PATH)
            .addToConfig("key.converter", "org.apache.kafka.connect.storage.StringConverter")
            .addToConfig("value.converter", "org.apache.kafka.connect.storage.StringConverter")
            .endSpec()
            .build());

        // Messages produced to Main Kafka Cluster (source) will be sinked to file, and mirrored into targeted Kafkas to later verify Operands work correctly.
        LOGGER.info("Transmit messages in Cluster: {}/{}", testStorage.getNamespaceName(), testStorage.getClusterName());
        final KafkaClients kafkaClients = ClientUtils.getInstantPlainClientBuilder(testStorage)
            .withPodSecurityPolicy(PodSecurityProfile.RESTRICTED)
            .build();
        resourceManager.createResourceWithWait(
            kafkaClients.producerStrimzi(),
            kafkaClients.consumerStrimzi()
        );
        ClientUtils.waitForInstantClientSuccess(testStorage);

        // verifies that Pods and Containers have proper generated SC
        final List<Pod> podsWithProperlyGeneratedSecurityCOntexts = PodUtils.getKafkaClusterPods(testStorage);
        podsWithProperlyGeneratedSecurityCOntexts.addAll(kubeClient().listPods(testStorage.getNamespaceName(), testStorage.getClusterName(), Labels.STRIMZI_KIND_LABEL, KafkaBridge.RESOURCE_KIND));
        podsWithProperlyGeneratedSecurityCOntexts.addAll(kubeClient().listPods(testStorage.getNamespaceName(), testStorage.getClusterName(), Labels.STRIMZI_KIND_LABEL, KafkaConnect.RESOURCE_KIND));
        podsWithProperlyGeneratedSecurityCOntexts.addAll(kubeClient().listPods(testStorage.getNamespaceName(), testStorage.getClusterName(), Labels.STRIMZI_KIND_LABEL, KafkaMirrorMaker2.RESOURCE_KIND));
        podsWithProperlyGeneratedSecurityCOntexts.addAll(kubeClient().listPods(testStorage.getNamespaceName(), testStorage.getClusterName(), Labels.STRIMZI_KIND_LABEL, KafkaMirrorMaker.RESOURCE_KIND));
        verifyPodAndContainerSecurityContext(podsWithProperlyGeneratedSecurityCOntexts);

        // verify KafkaConnect
        final String kafkaConnectPodName = kubeClient(testStorage.getNamespaceName()).listPods(testStorage.getNamespaceName(), testStorage.getClusterName(), Labels.STRIMZI_KIND_LABEL, KafkaConnect.RESOURCE_KIND).get(0).getMetadata().getName();
        KafkaConnectUtils.waitForMessagesInKafkaConnectFileSink(testStorage.getNamespaceName(), kafkaConnectPodName, TestConstants.DEFAULT_SINK_FILE_PATH, testStorage.getMessageCount());

        // verify MM1, as topic name does not change, only bootstrap server is changed.
        final KafkaClients mm1Client = ClientUtils.getInstantPlainClientBuilder(testStorage, KafkaResources.plainBootstrapAddress(mm1TargetClusterName))
            .withPodSecurityPolicy(PodSecurityProfile.RESTRICTED)
            .build();
        resourceManager.createResourceWithWait(mm1Client.consumerStrimzi());
        ClientUtils.waitForInstantConsumerClientSuccess(testStorage);

        // verify MM2
        final KafkaClients mm2Client = ClientUtils.getInstantPlainClientBuilder(testStorage, KafkaResources.plainBootstrapAddress(mm2TargetClusterName))
            .withTopicName(mm2SourceMirroredTopicName)
            .withPodSecurityPolicy(PodSecurityProfile.RESTRICTED)
            .build();
        resourceManager.createResourceWithWait(mm2Client.consumerStrimzi());
        ClientUtils.waitForInstantConsumerClientSuccess(testStorage);

        // verify that client incorrectly configured Pod Security Profile wont successfully communicate.
        final KafkaClients incorrectKafkaClients = new KafkaClientsBuilder(kafkaClients)
            .withPodSecurityPolicy(PodSecurityProfile.DEFAULT)
            .build();

        // excepted failure
        // job_controller.go:1437 pods "..." is forbidden: violates PodSecurity "restricted:latest": allowPrivilegeEscalation != false
        // (container "..." must set securityContext.allowPrivilegeEscalation=false),
        // unrestricted capabilities (container "..." must set securityContext.capabilities.drop=["ALL"]),
        // runAsNonRoot != true (pod or container "..." must set securityContext.runAsNonRoot=true),
        // seccompProfile (pod or container "..." must set securityContext.seccompProfile.type to "RuntimeDefault" or "Localhost")
        resourceManager.createResourceWithoutWait(incorrectKafkaClients.producerStrimzi());
        ClientUtils.waitForInstantProducerClientTimeout(testStorage);
    }
}