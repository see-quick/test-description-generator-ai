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

    @IsolatedTest("Specific Cluster Operator for test case")
    @TestDoc(description = @Desc("Test ensuring that NetworkPolicy generation is properly influenced by environment variables."), contact = @Contact(name = "Test Author", email = "test.author@example.com"), steps = { @Step(value = "value", expected = "expected"), @Step(value = "value", expected = "expected"), @Step(value = "value", expected = "expected"), @Step(value = "value", expected = "expected"), @Step(value = "value", expected = "expected"), @Step(value = "value", expected = "expected") }, useCases = { @UseCase(id = "id") }, tags = { @TestTag(value = "value"), @TestTag(value = "value") })
    void testNPWhenOperatorIsInDifferentNamespaceThanOperand() {
        assumeTrue(!Environment.isHelmInstall() && !Environment.isOlmInstall());
        final TestStorage testStorage = new TestStorage(ResourceManager.getTestContext());
        String secondNamespace = "second-" + Environment.TEST_SUITE_NAMESPACE;
        Map<String, String> labels = new HashMap<>();
        labels.put("my-label", "my-value");
        EnvVar operatorLabelsEnv = new EnvVarBuilder().withName("STRIMZI_OPERATOR_NAMESPACE_LABELS").withValue(labels.toString().replaceAll("\\{|}", "")).build();
        clusterOperator = new SetupClusterOperator.SetupClusterOperatorBuilder().withExtensionContext(ResourceManager.getTestContext()).withNamespace(Environment.TEST_SUITE_NAMESPACE).withWatchingNamespaces(TestConstants.WATCH_ALL_NAMESPACES).withBindingsNamespaces(Arrays.asList(Environment.TEST_SUITE_NAMESPACE, secondNamespace)).withExtraEnvVars(Collections.singletonList(operatorLabelsEnv)).createInstallation().runInstallation();
        Namespace actualNamespace = kubeClient().getClient().namespaces().withName(Environment.TEST_SUITE_NAMESPACE).get();
        kubeClient().getClient().namespaces().withName(Environment.TEST_SUITE_NAMESPACE).edit(ns -> new NamespaceBuilder(actualNamespace).editOrNewMetadata().addToLabels(labels).endMetadata().build());
        cluster.setNamespace(secondNamespace);
        resourceManager.createResourceWithWait(NodePoolsConverter.convertNodePoolsIfNeeded(KafkaNodePoolTemplates.brokerPoolPersistentStorage(secondNamespace, testStorage.getBrokerPoolName(), testStorage.getClusterName(), 3).build(), KafkaNodePoolTemplates.controllerPoolPersistentStorage(secondNamespace, testStorage.getControllerPoolName(), testStorage.getClusterName(), 3).build()));
        resourceManager.createResourceWithWait(KafkaTemplates.kafkaMetricsConfigMap(secondNamespace, testStorage.getClusterName()), KafkaTemplates.kafkaWithMetrics(secondNamespace, testStorage.getClusterName(), 3, 3).editMetadata().addToLabels(labels).endMetadata().build());
        checkNetworkPoliciesInNamespace(testStorage.getClusterName(), secondNamespace);
        changeKafkaConfigurationAndCheckObservedGeneration(testStorage.getClusterName(), secondNamespace);
    }

    @IsolatedTest("Specific Cluster Operator for test case")
    @Tag(CRUISE_CONTROL)
    @SkipDefaultNetworkPolicyCreation("NetworkPolicy generation from CO is disabled in this test, resulting in problems with connection" + " in case of that DENY ALL global NetworkPolicy is used")
    @Desc("Test ensuring that NetworkPolicy generation is properly influenced by environment variables.")
    @Contact(name = "Test Author", email = "test.author@example.com")
    @Step(value = "value", expected = "expected")
    @Step(value = "value", expected = "expected")
    @Step(value = "value", expected = "expected")
    @Step(value = "value", expected = "expected")
    @Step(value = "value", expected = "expected")
    @Step(value = "value", expected = "expected")
    @UseCases(id = "id")
    @Tags(value = "value", value = "value")
    @TestDoc(description = @Desc("Test ensuring that NetworkPolicy generation is properly influenced by environment variables."), contact = @Contact(name = "Test Author", email = "test.author@example.com"), steps = { @Step(value = "value", expected = "expected"), @Step(value = "value", expected = "expected"), @Step(value = "value", expected = "expected"), @Step(value = "value", expected = "expected"), @Step(value = "value", expected = "expected"), @Step(value = "value", expected = "expected") }, useCases = { @UseCase(id = "id") }, tags = { @TestTag(value = "value"), @TestTag(value = "value") })
    void testNPGenerationEnvironmentVariable() {
        assumeTrue(!Environment.isHelmInstall() && !Environment.isOlmInstall());
        final TestStorage testStorage = new TestStorage(ResourceManager.getTestContext());
        EnvVar networkPolicyGenerationEnv = new EnvVarBuilder().withName("STRIMZI_NETWORK_POLICY_GENERATION").withValue("false").build();
        clusterOperator = new SetupClusterOperator.SetupClusterOperatorBuilder().withExtensionContext(ResourceManager.getTestContext()).withNamespace(Environment.TEST_SUITE_NAMESPACE).withExtraEnvVars(Collections.singletonList(networkPolicyGenerationEnv)).createInstallation().runInstallation();
        resourceManager.createResourceWithWait(NodePoolsConverter.convertNodePoolsIfNeeded(KafkaNodePoolTemplates.brokerPool(testStorage.getNamespaceName(), testStorage.getBrokerPoolName(), testStorage.getClusterName(), 3).build(), KafkaNodePoolTemplates.controllerPool(testStorage.getNamespaceName(), testStorage.getControllerPoolName(), testStorage.getClusterName(), 3).build()));
        resourceManager.createResourceWithWait(KafkaTemplates.kafkaWithCruiseControl(testStorage.getClusterName(), 3, 3).build());
        resourceManager.createResourceWithWait(KafkaConnectTemplates.kafkaConnect(testStorage.getClusterName(), Environment.TEST_SUITE_NAMESPACE, 1).build());
        List<NetworkPolicy> networkPolicyList = kubeClient().getClient().network().networkPolicies().list().getItems().stream().filter(item -> item.getMetadata().getLabels() != null && item.getMetadata().getLabels().containsKey("strimzi.io/name")).collect(Collectors.toList());
        assertThat("List of NetworkPolicies generated by Strimzi is not empty.", networkPolicyList, is(Collections.EMPTY_LIST));
    }

    void checkNetworkPoliciesInNamespace(String clusterName, String namespace) {
        List<NetworkPolicy> networkPolicyList = new ArrayList<>(kubeClient().getClient().network().networkPolicies().inNamespace(namespace).list().getItems());
        assertNotNull(networkPolicyList.stream().filter(networkPolicy -> networkPolicy.getMetadata().getName().contains(KafkaResources.kafkaNetworkPolicyName(clusterName))).findFirst());
        assertNotNull(networkPolicyList.stream().filter(networkPolicy -> networkPolicy.getMetadata().getName().contains(KafkaResources.zookeeperNetworkPolicyName(clusterName))).findFirst());
        assertNotNull(networkPolicyList.stream().filter(networkPolicy -> networkPolicy.getMetadata().getName().contains(KafkaResources.entityOperatorDeploymentName(clusterName))).findFirst());
        // if KE is enabled
        if (KafkaResource.kafkaClient().inNamespace(namespace).withName(clusterName).get().getSpec().getKafkaExporter() != null) {
            assertNotNull(networkPolicyList.stream().filter(networkPolicy -> networkPolicy.getMetadata().getName().contains(KafkaExporterResources.componentName(clusterName))).findFirst());
        }
    }

    void changeKafkaConfigurationAndCheckObservedGeneration(String clusterName, String namespace) {
        long observedGen = KafkaResource.kafkaClient().inNamespace(namespace).withName(clusterName).get().getStatus().getObservedGeneration();
        KafkaUtils.updateConfigurationWithStabilityWait(namespace, clusterName, "log.message.timestamp.type", "LogAppendTime");
        assertThat(KafkaResource.kafkaClient().inNamespace(namespace).withName(clusterName).get().getStatus().getObservedGeneration(), is(not(observedGen)));
    }
}
