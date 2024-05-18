/*
 * Copyright Strimzi authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.strimzi.systemtest.security;

import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.EnvVarBuilder;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.networking.v1.NetworkPolicy;
import io.fabric8.kubernetes.api.model.networking.v1.NetworkPolicyPeerBuilder;
import io.skodjob.testframe.MetricsCollector;
import io.strimzi.api.kafka.model.kafka.KafkaResources;
import io.strimzi.api.kafka.model.kafka.exporter.KafkaExporterResources;
import io.strimzi.api.kafka.model.kafka.listener.GenericKafkaListenerBuilder;
import io.strimzi.api.kafka.model.kafka.listener.KafkaListenerType;
import io.strimzi.systemtest.AbstractST;
import io.strimzi.systemtest.Environment;
import io.strimzi.systemtest.TestConstants;
import io.strimzi.systemtest.annotations.IsolatedTest;
import io.strimzi.systemtest.annotations.SkipDefaultNetworkPolicyCreation;
import io.strimzi.systemtest.kafkaclients.internalClients.KafkaClients;
import io.strimzi.systemtest.metrics.KafkaExporterMetricsComponent;
import io.strimzi.systemtest.resources.NodePoolsConverter;
import io.strimzi.systemtest.resources.ResourceManager;
import io.strimzi.systemtest.resources.crd.KafkaResource;
import io.strimzi.systemtest.resources.operator.SetupClusterOperator;
import io.strimzi.systemtest.storage.TestStorage;
import io.strimzi.systemtest.templates.crd.KafkaConnectTemplates;
import io.strimzi.systemtest.templates.crd.KafkaNodePoolTemplates;
import io.strimzi.systemtest.templates.crd.KafkaTemplates;
import io.strimzi.systemtest.templates.crd.KafkaTopicTemplates;
import io.strimzi.systemtest.templates.crd.KafkaUserTemplates;
import io.strimzi.systemtest.templates.specific.ScraperTemplates;
import io.strimzi.systemtest.utils.ClientUtils;
import io.strimzi.systemtest.utils.kafkaUtils.KafkaUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.strimzi.systemtest.TestConstants.CRUISE_CONTROL;
import static io.strimzi.systemtest.TestConstants.INTERNAL_CLIENTS_USED;
import static io.strimzi.systemtest.TestConstants.NETWORKPOLICIES_SUPPORTED;
import static io.strimzi.systemtest.TestConstants.REGRESSION;
import static io.strimzi.test.k8s.KubeClusterResource.kubeClient;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Tag(NETWORKPOLICIES_SUPPORTED)
@Tag(REGRESSION)
public class NetworkPoliciesST extends AbstractST {
    private static final Logger LOGGER = LogManager.getLogger(NetworkPoliciesST.class);

    /**
     * @description This test case verifies network policies if Cluster Operator is in same namespace as Operands.
     *
     * @steps
     *  1. - Deploy Kafka with plain and tls listeners, having set network policies for respective clients
     *     - Kafka is deployed
     *  2. - Deploy KafkaTopics, metric scraper Pod and all Kafka clients
     *     - All resources are deployed
     *  3. - Produce and Consume messages with clients using respective listeners
     *     - Clients Fails or Succeed in connecting to the Kafka based on specified network policies
     *  4. - Scrape exported metrics regarding present Kafka Topics
     *     - Metrics are present and have expected values
     *  5. - Change log configuration in Kafka config
     *     - Observed generation is higher than before the config change and Rolling Update did not take place.
     *
     * @usecase
     *  - network-policy
     *  - listeners
     *  - metrics
     */
    @IsolatedTest("Specific Cluster Operator for test case")
    @Tag(INTERNAL_CLIENTS_USED)
    @SuppressWarnings("checkstyle:MethodLength")
    @TestDoc(description = @Desc("This test case verifies network policies if Cluster Operator is in same namespace as Operands."), contact = @Contact(name = "see-quick", email = "maros.orsak159@gmail.com"), steps = { @Step(value = "Deploy Kafka with plain and tls listeners, having set network policies for respective clients", expected = "Kafka is deployed"), @Step(value = "Deploy KafkaTopics, metric scraper Pod and all Kafka clients", expected = "All resources are deployed"), @Step(value = "Produce and Consume messages with clients using respective listeners", expected = "Clients Fails or Succeed in connecting to the Kafka based on specified network policies"), @Step(value = "Scrape exported metrics regarding present Kafka Topics", expected = "Metrics are present and have expected values"), @Step(value = "Change log configuration in Kafka config", expected = "Observed generation is higher than before the config change and Rolling Update did not take place.") }, useCases = { @UseCase(id = "network-policy"), @UseCase(id = "listeners"), @UseCase(id = "metrics") }, tags = { @TestTag(value = "default"), @TestTag(value = "regression") })
    void testNetworkPoliciesOnListenersWhenOperatorIsInSameNamespaceAsOperands() {
        assumeTrue(!Environment.isHelmInstall() && !Environment.isOlmInstall());

        final TestStorage testStorage = new TestStorage(ResourceManager.getTestContext());

        final String topicNameAccessedTls = testStorage.getTopicName() + "-accessed-tls";
        final String topicNameAccessedPlain = testStorage.getTopicName() + "-accessed-plain";
        final String topicNameDeniedTls = testStorage.getTopicName() + "-denied-tls";
        final String topicNameDeniedPlain = testStorage.getTopicName() + "-denied-plain";

        final String producerNameAccessedTls = testStorage.getProducerName() + "-accessed-tls";
        final String producerNameAccessedPlain = testStorage.getProducerName() + "-accessed-plain";
        final String consumerNameAccessedTls = testStorage.getConsumerName() + "-accessed-tls";
        final String consumerNameAccessedPlain = testStorage.getConsumerName() + "-accessed-plain";

        final String producerNameDeniedTls = testStorage.getProducerName() + "-denied-tls";
        final String producerNameDeniedPlain = testStorage.getProducerName() + "-denied-plain";
        final String consumerNameDeniedTls = testStorage.getConsumerName() + "-denied-tls";
        final String consumerNameDeniedPlain = testStorage.getConsumerName() + "-denied-plain";

        clusterOperator = new SetupClusterOperator.SetupClusterOperatorBuilder()
            .withExtensionContext(ResourceManager.getTestContext())
            .withNamespace(testStorage.getNamespaceName())
            .createInstallation()
            .runInstallation();

        resourceManager.createResourceWithWait(
            NodePoolsConverter.convertNodePoolsIfNeeded(
                KafkaNodePoolTemplates.brokerPoolPersistentStorage(testStorage.getNamespaceName(), testStorage.getBrokerPoolName(), testStorage.getClusterName(), 1).build(),
                KafkaNodePoolTemplates.controllerPoolPersistentStorage(testStorage.getNamespaceName(), testStorage.getControllerPoolName(), testStorage.getClusterName(), 1).build()
            )
        );
        resourceManager.createResourceWithWait(KafkaTemplates.kafkaPersistent(testStorage.getClusterName(), 1, 1)
                .editSpec()
                .editKafka()
                .withListeners(
                    new GenericKafkaListenerBuilder()
                        .withName(TestConstants.PLAIN_LISTENER_DEFAULT_NAME)
                        .withPort(9092)
                        .withType(KafkaListenerType.INTERNAL)
                        .withTls(false)
                        .withNewKafkaListenerAuthenticationScramSha512Auth()
                        .endKafkaListenerAuthenticationScramSha512Auth()
                        .withNetworkPolicyPeers(
                            new NetworkPolicyPeerBuilder()
                                .withNewPodSelector()
                                .addToMatchLabels("app", producerNameAccessedPlain)
                                .endPodSelector()
                                .build(),
                            new NetworkPolicyPeerBuilder()
                                .withNewPodSelector()
                                .addToMatchLabels("app", consumerNameAccessedPlain)
                                .endPodSelector()
                                .build()
                        )
                        .build(),
                    new GenericKafkaListenerBuilder()
                        .withName(TestConstants.TLS_LISTENER_DEFAULT_NAME)
                        .withPort(9093)
                        .withType(KafkaListenerType.INTERNAL)
                        .withTls(true)
                        .withNewKafkaListenerAuthenticationScramSha512Auth()
                        .endKafkaListenerAuthenticationScramSha512Auth()
                        .withNetworkPolicyPeers(
                            new NetworkPolicyPeerBuilder()
                                .withNewPodSelector()
                                .addToMatchLabels("app", producerNameAccessedTls)
                                .endPodSelector()
                                .build(),
                            new NetworkPolicyPeerBuilder()
                                .withNewPodSelector()
                                .addToMatchLabels("app", consumerNameAccessedTls)
                                .endPodSelector()
                                .build()
                        )
                        .build()
                )
                .endKafka()
                .withNewKafkaExporter()
                .endKafkaExporter()
                .endSpec()
                .build(),
            ScraperTemplates.scraperPod(testStorage.getNamespaceName(), testStorage.getScraperName()).build(),
            KafkaUserTemplates.scramShaUser(testStorage).build(),
            KafkaTopicTemplates.topic(testStorage.getClusterName(), topicNameAccessedPlain, testStorage.getNamespaceName()).build(),
            KafkaTopicTemplates.topic(testStorage.getClusterName(), topicNameAccessedTls, testStorage.getNamespaceName()).build(),
            KafkaTopicTemplates.topic(testStorage.getClusterName(), topicNameDeniedPlain, testStorage.getNamespaceName()).build(),
            KafkaTopicTemplates.topic(testStorage.getClusterName(), topicNameDeniedTls, testStorage.getNamespaceName()).build()
        );

        LOGGER.info("Initialize producers and consumers with access to the Kafka using plain and tls listeners");
        final KafkaClients kafkaClientsWithAccessPlain = ClientUtils.getInstantScramShaOverPlainClientBuilder(testStorage)
            .withProducerName(producerNameAccessedPlain)
            .withConsumerName(consumerNameAccessedPlain)
            .withTopicName(topicNameAccessedPlain)
            .build();
        final KafkaClients kafkaClientsWithAccessTls = ClientUtils.getInstantScramShaOverTlsClientBuilder(testStorage)
            .withProducerName(producerNameAccessedTls)
            .withConsumerName(consumerNameAccessedTls)
            .withTopicName(topicNameAccessedTls)
            .build();

        LOGGER.info("Initialize producers and consumers without access (denied) to the Kafka using plain and tls listeners");
        final KafkaClients kafkaClientsWithoutAccessPlain = ClientUtils.getInstantScramShaOverPlainClientBuilder(testStorage)
            .withProducerName(producerNameDeniedPlain)
            .withConsumerName(consumerNameDeniedPlain)
            .withTopicName(topicNameDeniedPlain)
            .build();
        final KafkaClients kafkaClientsWithoutAccessTls = ClientUtils.getInstantScramShaOverTlsClientBuilder(testStorage)
            .withProducerName(producerNameDeniedTls)
            .withConsumerName(consumerNameDeniedTls)
            .withTopicName(topicNameDeniedTls)
            .build();

        LOGGER.info("Deploy all initialized clients");
        resourceManager.createResourceWithWait(
            kafkaClientsWithAccessPlain.producerScramShaPlainStrimzi(),
            kafkaClientsWithAccessPlain.consumerScramShaPlainStrimzi(),
            kafkaClientsWithAccessTls.producerScramShaTlsStrimzi(testStorage.getClusterName()),
            kafkaClientsWithAccessTls.consumerScramShaTlsStrimzi(testStorage.getClusterName()),
            kafkaClientsWithoutAccessPlain.producerScramShaPlainStrimzi(),
            kafkaClientsWithoutAccessPlain.consumerScramShaPlainStrimzi(),
            kafkaClientsWithoutAccessTls.producerScramShaTlsStrimzi(testStorage.getClusterName()),
            kafkaClientsWithoutAccessTls.consumerScramShaTlsStrimzi(testStorage.getClusterName())
        );

        LOGGER.info("Verifying that clients: {}, {}, {}, {} are all allowed to communicate", producerNameAccessedPlain, consumerNameAccessedPlain, producerNameAccessedTls, consumerNameAccessedTls);
        ClientUtils.waitForClientsSuccess(producerNameAccessedPlain, consumerNameAccessedPlain, testStorage.getNamespaceName(), testStorage.getMessageCount());
        ClientUtils.waitForClientsSuccess(producerNameAccessedTls, consumerNameAccessedTls, testStorage.getNamespaceName(), testStorage.getMessageCount());

        LOGGER.info("Verifying that clients: {}, {}, {}, {} are denied to communicate", producerNameDeniedPlain, consumerNameDeniedPlain, producerNameDeniedTls, consumerNameDeniedTls);
        ClientUtils.waitForClientsTimeout(producerNameDeniedPlain, consumerNameDeniedPlain, testStorage.getNamespaceName(), testStorage.getMessageCount());
        ClientUtils.waitForClientsTimeout(producerNameDeniedTls, consumerNameDeniedTls, testStorage.getNamespaceName(), testStorage.getMessageCount());

        LOGGER.info("Check metrics exported by KafkaExporter");

        final String scraperPodName = kubeClient().listPodsByPrefixInName(testStorage.getNamespaceName(), testStorage.getScraperName()).get(0).getMetadata().getName();
        MetricsCollector metricsCollector = new MetricsCollector.Builder()
            .withScraperPodName(scraperPodName)
            .withComponent(KafkaExporterMetricsComponent.create(testStorage.getNamespaceName(), testStorage.getClusterName()))
            .build();

        metricsCollector.collectMetricsFromPods();
        assertThat("KafkaExporter metrics should be non-empty", metricsCollector.getCollectedData().size() > 0);
        for (Map.Entry<String, String> entry : metricsCollector.getCollectedData().entrySet()) {
            assertThat("Value from collected metric should be non-empty", !entry.getValue().isEmpty());
            assertThat("Metrics doesn't contain specific values", entry.getValue().contains("kafka_consumergroup_current_offset"));
            assertThat("Metrics doesn't contain specific values", entry.getValue().contains("kafka_topic_partitions{topic=\"" + topicNameAccessedTls + "\"} 1"));
            assertThat("Metrics doesn't contain specific values", entry.getValue().contains("kafka_topic_partitions{topic=\"" + topicNameAccessedPlain + "\"} 1"));
        }

        checkNetworkPoliciesInNamespace(testStorage.getClusterName(), Environment.TEST_SUITE_NAMESPACE);
        changeKafkaConfigurationAndCheckObservedGeneration(testStorage.getClusterName(), Environment.TEST_SUITE_NAMESPACE);
    }

    @IsolatedTest("Specific Cluster Operator for test case")
    @TestDoc(description = @Desc("Test checking the behavior of a Cluster Operator when it is located in a different namespace than its operand."), contact = @Contact(name = "see-quick", email = "maros.orsak159@gmail.com"), steps = { @Step(value = "Check if the installation is not Helm or OLM", expected = "Proceed if neither Helm nor OLM is installed"), @Step(value = "Initialize storage and set up namespaces", expected = "Test storage initialized and second namespace created"), @Step(value = "Define and set labels for namespaces", expected = "Labels defined and applied to namespaces"), @Step(value = "Install Cluster Operator in the primary namespace", expected = "Cluster Operator installed with specified labels and environment variables"), @Step(value = "Create resources in the second namespace", expected = "Resources created and configured in the second namespace"), @Step(value = "Verify that network policies apply in the second namespace", expected = "Network policies checked and verified"), @Step(value = "Change Kafka configuration and check observed generation", expected = "Configuration changed and observed generation verified") }, useCases = { @UseCase(id = "cluster-operator-different-namespace") }, tags = { @TestTag(value = "cluster-operator"), @TestTag(value = "namespace"), @TestTag(value = "network-policy"), @TestTag(value = "kafka") })
    void testNPWhenOperatorIsInDifferentNamespaceThanOperand() {
        assumeTrue(!Environment.isHelmInstall() && !Environment.isOlmInstall());

        final TestStorage testStorage = new TestStorage(ResourceManager.getTestContext());
        String secondNamespace = "second-" + Environment.TEST_SUITE_NAMESPACE;

        Map<String, String> labels = new HashMap<>();
        labels.put("my-label", "my-value");

        EnvVar operatorLabelsEnv = new EnvVarBuilder()
            .withName("STRIMZI_OPERATOR_NAMESPACE_LABELS")
            .withValue(labels.toString().replaceAll("\\{|}", ""))
            .build();

        clusterOperator = new SetupClusterOperator.SetupClusterOperatorBuilder()
            .withExtensionContext(ResourceManager.getTestContext())
            .withNamespace(Environment.TEST_SUITE_NAMESPACE)
            .withWatchingNamespaces(TestConstants.WATCH_ALL_NAMESPACES)
            .withBindingsNamespaces(Arrays.asList(Environment.TEST_SUITE_NAMESPACE, secondNamespace))
            .withExtraEnvVars(Collections.singletonList(operatorLabelsEnv))
            .createInstallation()
            .runInstallation();

        Namespace actualNamespace = kubeClient().getClient().namespaces().withName(Environment.TEST_SUITE_NAMESPACE).get();
        kubeClient().getClient().namespaces().withName(Environment.TEST_SUITE_NAMESPACE).edit(ns -> new NamespaceBuilder(actualNamespace)
            .editOrNewMetadata()
            .addToLabels(labels)
            .endMetadata()
            .build());

        cluster.setNamespace(secondNamespace);

        resourceManager.createResourceWithWait(
            NodePoolsConverter.convertNodePoolsIfNeeded(
                KafkaNodePoolTemplates.brokerPoolPersistentStorage(secondNamespace, testStorage.getBrokerPoolName(), testStorage.getClusterName(), 3).build(),
                KafkaNodePoolTemplates.controllerPoolPersistentStorage(secondNamespace, testStorage.getControllerPoolName(), testStorage.getClusterName(), 3).build()
            )
        );
        resourceManager.createResourceWithWait(
            KafkaTemplates.kafkaMetricsConfigMap(secondNamespace, testStorage.getClusterName()),
            KafkaTemplates.kafkaWithMetrics(secondNamespace, testStorage.getClusterName(), 3, 3)
                .editMetadata()
                .addToLabels(labels)
                .endMetadata()
                .build()
        );

        checkNetworkPoliciesInNamespace(testStorage.getClusterName(), secondNamespace);

        changeKafkaConfigurationAndCheckObservedGeneration(testStorage.getClusterName(), secondNamespace);
    }

    @IsolatedTest("Specific Cluster Operator for test case")
    @Tag(CRUISE_CONTROL)
    @SkipDefaultNetworkPolicyCreation("NetworkPolicy generation from CO is disabled in this test, resulting in problems with connection" +
        " in case of that DENY ALL global NetworkPolicy is used")
    @TestDoc(description = @Desc("Test verifying that NetworkPolicy is not generated when STRIMZI_NETWORK_POLICY_GENERATION is set to false."), contact = @Contact(name = "Maros Orsak", email = "maros.orsak159@gmail.com"), steps = { @Step(value = "Assume Helm and OLM are not used", expected = "Test continues as Helm and OLM are not being used"), @Step(value = "Set STRIMZI_NETWORK_POLICY_GENERATION environment variable to false", expected = "Environment variable is set correctly"), @Step(value = "Install the Cluster Operator", expected = "Cluster Operator is installed without generating NetworkPolicies"), @Step(value = "Create necessary Kafka resources", expected = "Kafka resources including brokers, Cruise Control, and Kafka Connect are created"), @Step(value = "Fetch the list of NetworkPolicies", expected = "List of NetworkPolicies is fetched"), @Step(value = "Verify that no NetworkPolicies have been generated by Strimzi", expected = "No NetworkPolicies are found") }, useCases = { @UseCase(id = "network-policy"), @UseCase(id = "cluster-operator"), @UseCase(id = "kafka-resources") }, tags = { @TestTag(value = "CRUISE_CONTROL"), @TestTag(value = "NetworkPolicy"), @TestTag(value = "ClusterOperator") })
    void testNPGenerationEnvironmentVariable() {
        assumeTrue(!Environment.isHelmInstall() && !Environment.isOlmInstall());

        final TestStorage testStorage = new TestStorage(ResourceManager.getTestContext());

        EnvVar networkPolicyGenerationEnv = new EnvVarBuilder()
            .withName("STRIMZI_NETWORK_POLICY_GENERATION")
            .withValue("false")
            .build();

        clusterOperator = new SetupClusterOperator.SetupClusterOperatorBuilder()
            .withExtensionContext(ResourceManager.getTestContext())
            .withNamespace(Environment.TEST_SUITE_NAMESPACE)
            .withExtraEnvVars(Collections.singletonList(networkPolicyGenerationEnv))
            .createInstallation()
            .runInstallation();

        resourceManager.createResourceWithWait(
            NodePoolsConverter.convertNodePoolsIfNeeded(
                KafkaNodePoolTemplates.brokerPool(testStorage.getNamespaceName(), testStorage.getBrokerPoolName(), testStorage.getClusterName(), 3).build(),
                KafkaNodePoolTemplates.controllerPool(testStorage.getNamespaceName(), testStorage.getControllerPoolName(), testStorage.getClusterName(), 3).build()
            )
        );
        resourceManager.createResourceWithWait(KafkaTemplates.kafkaWithCruiseControl(testStorage.getClusterName(), 3, 3)
            .build());

        resourceManager.createResourceWithWait(KafkaConnectTemplates.kafkaConnect(testStorage.getClusterName(), Environment.TEST_SUITE_NAMESPACE, 1)
            .build());

        List<NetworkPolicy> networkPolicyList = kubeClient().getClient().network().networkPolicies().list().getItems().stream()
            .filter(item -> item.getMetadata().getLabels() != null && item.getMetadata().getLabels().containsKey("strimzi.io/name"))
            .collect(Collectors.toList());

        assertThat("List of NetworkPolicies generated by Strimzi is not empty.", networkPolicyList, is(Collections.EMPTY_LIST));
    }

    void checkNetworkPoliciesInNamespace(String clusterName, String namespace) {
        List<NetworkPolicy> networkPolicyList = new ArrayList<>(kubeClient().getClient().network().networkPolicies().inNamespace(namespace).list().getItems());

        assertNotNull(networkPolicyList.stream().filter(networkPolicy ->  networkPolicy.getMetadata().getName().contains(KafkaResources.kafkaNetworkPolicyName(clusterName))).findFirst());
        assertNotNull(networkPolicyList.stream().filter(networkPolicy ->  networkPolicy.getMetadata().getName().contains(KafkaResources.zookeeperNetworkPolicyName(clusterName))).findFirst());
        assertNotNull(networkPolicyList.stream().filter(networkPolicy ->  networkPolicy.getMetadata().getName().contains(KafkaResources.entityOperatorDeploymentName(clusterName))).findFirst());

        // if KE is enabled
        if (KafkaResource.kafkaClient().inNamespace(namespace).withName(clusterName).get().getSpec().getKafkaExporter() != null) {
            assertNotNull(networkPolicyList.stream().filter(networkPolicy ->  networkPolicy.getMetadata().getName().contains(KafkaExporterResources.componentName(clusterName))).findFirst());
        }
    }

    void changeKafkaConfigurationAndCheckObservedGeneration(String clusterName, String namespace) {
        long observedGen = KafkaResource.kafkaClient().inNamespace(namespace).withName(clusterName).get().getStatus().getObservedGeneration();
        KafkaUtils.updateConfigurationWithStabilityWait(namespace, clusterName, "log.message.timestamp.type", "LogAppendTime");

        assertThat(KafkaResource.kafkaClient().inNamespace(namespace).withName(clusterName).get().getStatus().getObservedGeneration(), is(not(observedGen)));
    }
}
