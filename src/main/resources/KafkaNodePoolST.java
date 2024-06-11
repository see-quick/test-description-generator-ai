/*
 * Copyright Strimzi authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.strimzi.systemtest.kafka;


import io.fabric8.kubernetes.api.model.LabelSelector;
import io.strimzi.api.kafka.model.nodepool.ProcessRoles;
import io.strimzi.api.kafka.model.topic.KafkaTopic;
import io.strimzi.operator.common.Annotations;
import io.strimzi.systemtest.AbstractST;
import io.strimzi.systemtest.Environment;
import io.strimzi.systemtest.annotations.ParallelNamespaceTest;
import io.strimzi.systemtest.kafkaclients.internalClients.KafkaClients;
import io.strimzi.systemtest.resources.NodePoolsConverter;
import io.strimzi.systemtest.resources.ResourceManager;
import io.strimzi.systemtest.resources.crd.KafkaNodePoolResource;
import io.strimzi.systemtest.resources.crd.KafkaResource;
import io.strimzi.systemtest.storage.TestStorage;
import io.strimzi.systemtest.templates.crd.KafkaNodePoolTemplates;
import io.strimzi.systemtest.templates.crd.KafkaTemplates;
import io.strimzi.systemtest.templates.crd.KafkaTopicTemplates;
import io.strimzi.systemtest.utils.ClientUtils;
import io.strimzi.systemtest.utils.RollingUpdateUtils;
import io.strimzi.systemtest.utils.kafkaUtils.KafkaNodePoolUtils;
import io.strimzi.systemtest.utils.kafkaUtils.KafkaTopicUtils;
import io.strimzi.systemtest.utils.kafkaUtils.KafkaUtils;
import io.strimzi.systemtest.utils.kubeUtils.controllers.StrimziPodSetUtils;
import io.strimzi.systemtest.utils.kubeUtils.objects.PodUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.strimzi.operator.common.Util.hashStub;
import static io.strimzi.systemtest.TestConstants.REGRESSION;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;


@Tag(REGRESSION)
@SuiteDoc(
    description = @Desc("This test case verifies the possibility of adding and removing Kafka Node Pools into an existing Kafka cluster."),
    contact = @Contact(name = "see-quick", email = "maros.orsak159@gmail.com"),
    beforeTestSteps = {
<<<<<<< Updated upstream
        @Step(value = "Deploy a Kafka instance with annotations to manage Node Pools and Initial 2 NodePools, one being controller if possible other initial broker.", expected = "Kafka instance is deployed according to Kafka and KafkaNodePool custom resource."),,
        @Step(value = "Create KafkaTopic with replica number requiring all Kafka Brokers to be present, Deploy clients and transmit messages and remove KafkaTopic.", expected = "Transition of messages is finished successfully, KafkaTopic created and cleaned as expected."),,
        @Step(value = "Add extra KafkaNodePool with broker role to the Kafka.", expected = "KafkaNodePool is deployed and ready."),,
        @Step(value = "Create KafkaTopic with replica number requiring all Kafka Brokers to be present, Deploy clients and transmit messages and remove KafkaTopic.", expected = "Transition of messages is finished successfully, KafkaTopic created and cleaned as expected."),,
        @Step(value = "Remove one of kafkaNodePool with broker role.", expected = "KafkaNodePool is removed, Pods are deleted, but other pods in Kafka are stable and ready."),,
        @Step(value = "Create KafkaTopic with replica number requiring all the remaining Kafka Brokers to be present, Deploy clients and transmit messages and remove KafkaTopic.", expected = "Transition of messages is finished successfully, KafkaTopic created and cleaned as expected.")
=======
        @Step(value = "Deploy a Kafka instance with annotations to manage Node Pools and initial 2 NodePools, one being controller if possible other initial broker.", expected = "Kafka instance is deployed according to Kafka and KafkaNodePool custom resource."),
        @Step(value = "Create KafkaTopic with replica number requiring all Kafka Brokers to be present, deploy clients, transmit messages and remove KafkaTopic.", expected = "Transition of messages is finished successfully, KafkaTopic created and cleaned as expected."),
        @Step(value = "Add extra KafkaNodePool with broker role to the Kafka.", expected = "KafkaNodePool is deployed and ready."),
        @Step(value = "Create KafkaTopic with replica number requiring all Kafka Brokers to be present, deploy clients, transmit messages and remove KafkaTopic.", expected = "Transition of messages is finished successfully, KafkaTopic created and cleaned as expected."),
        @Step(value = "Remove one of the kafkaNodePools with broker role.", expected = "KafkaNodePool is removed, pods are deleted, but other pods in Kafka are stable and ready."),
        @Step(value = "Create KafkaTopic with replica number requiring all the remaining Kafka Brokers to be present, deploy clients, transmit messages and remove KafkaTopic.", expected = "Transition of messages is finished successfully, KafkaTopic created and cleaned as expected.")
>>>>>>> Stashed changes
    },
    useCases = {
        @UseCase(id = "kafka-node-pool")
    },
    tags = {
<<<<<<< Updated upstream
        @TestTag(value = "integration"),
=======
>>>>>>> Stashed changes
        @TestTag(value = "regression")
    }
)
public class KafkaNodePoolST extends AbstractST {
    private static final Logger LOGGER = LogManager.getLogger(KafkaNodePoolST.class);

    /**
     * @description This test case verifies possibility of adding and removing Kafka Node Pools into existing Kafka cluster.
     *
     * @steps
     *  1. - Deploy a Kafka instance with annotations to manage Node Pools and Initial 2 NodePools, one being controller if possible other initial broker.
     *     - Kafka instance is deployed according to Kafka and KafkaNodePool custom resource.
     *  2. - Create KafkaTopic with replica number requiring all Kafka Brokers to be present, Deploy clients and transmit messages and remove KafkaTopic.
     *     - transition of messages is finished successfully, KafkaTopic created and cleaned as expected.
     *  3. - Add extra KafkaNodePool with broker role to the Kafka.
     *     - KafkaNodePool is deployed and ready.
     *  4. - Create KafkaTopic with replica number requiring all Kafka Brokers to be present, Deploy clients and transmit messages and remove KafkaTopic.
     *     - transition of messages is finished successfully, KafkaTopic created and cleaned as expected.
     *  5. - Remove one of kafkaNodePool with broker role.
     *     - KafkaNodePool is removed, Pods are deleted, but other pods in Kafka are stable and ready.
     *  6. - Create KafkaTopic with replica number requiring all the remaining Kafka Brokers to be present, Deploy clients and transmit messages and remove KafkaTopic.
     *     - transition of messages is finished successfully, KafkaTopic created and cleaned as expected.
     *
     * @usecase
     *  - kafka-node-pool
     */
    @ParallelNamespaceTest
    @TestDoc(
        description = @Desc("This test case verifies the possibility of adding and removing Kafka Node Pools into an existing Kafka cluster."),
        contact = @Contact(name = "see-quick", email = "maros.orsak159@gmail.com"),
        steps = {
<<<<<<< Updated upstream
            @Step(value = "Deploy a Kafka instance with annotations to manage Node Pools and Initial 2 NodePools, one being controller if possible other initial broker.", expected = "Kafka instance is deployed according to Kafka and KafkaNodePool custom resource."),
            @Step(value = "Create KafkaTopic with replica number requiring all Kafka Brokers to be present, Deploy clients and transmit messages and remove KafkaTopic.", expected = "Transition of messages is finished successfully, KafkaTopic created and cleaned as expected."),
            @Step(value = "Add extra KafkaNodePool with broker role to the Kafka.", expected = "KafkaNodePool is deployed and ready."),
            @Step(value = "Create KafkaTopic with replica number requiring all Kafka Brokers to be present, Deploy clients and transmit messages and remove KafkaTopic.", expected = "Transition of messages is finished successfully, KafkaTopic created and cleaned as expected."),
            @Step(value = "Remove one of kafkaNodePool with broker role.", expected = "KafkaNodePool is removed, Pods are deleted, but other pods in Kafka are stable and ready."),
            @Step(value = "Create KafkaTopic with replica number requiring all the remaining Kafka Brokers to be present, Deploy clients and transmit messages and remove KafkaTopic.", expected = "Transition of messages is finished successfully, KafkaTopic created and cleaned as expected.")
=======
            @Step(value = "Deploy a Kafka instance with annotations to manage Node Pools and initial 2 NodePools, one being controller if possible other initial broker.", expected = "Kafka instance is deployed according to Kafka and KafkaNodePool custom resource."),
            @Step(value = "Create KafkaTopic with replica number requiring all Kafka Brokers to be present, deploy clients, transmit messages and remove KafkaTopic.", expected = "Transition of messages is finished successfully, KafkaTopic created and cleaned as expected."),
            @Step(value = "Add extra KafkaNodePool with broker role to the Kafka.", expected = "KafkaNodePool is deployed and ready."),
            @Step(value = "Create KafkaTopic with replica number requiring all Kafka Brokers to be present, deploy clients, transmit messages and remove KafkaTopic.", expected = "Transition of messages is finished successfully, KafkaTopic created and cleaned as expected."),
            @Step(value = "Remove one of the kafkaNodePools with broker role.", expected = "KafkaNodePool is removed, pods are deleted, but other pods in Kafka are stable and ready."),
            @Step(value = "Create KafkaTopic with replica number requiring all the remaining Kafka Brokers to be present, deploy clients, transmit messages and remove KafkaTopic.", expected = "Transition of messages is finished successfully, KafkaTopic created and cleaned as expected.")
>>>>>>> Stashed changes
        },
        useCases = {
            @UseCase(id = "kafka-node-pool")
        },
        tags = {
<<<<<<< Updated upstream
            @TestTag(value = "integration"),
=======
>>>>>>> Stashed changes
            @TestTag(value = "regression")
        }
    )
    void testNodePoolsAdditionAndRemoval() {
        final TestStorage testStorage = new TestStorage(ResourceManager.getTestContext());
        // node pools name convention is 'A' for all roles (: if possible i.e. based on feature gate) 'B' for broker roles.
        final String poolAName = testStorage.getBrokerPoolName() + "-a";
        final String poolB1Name = testStorage.getBrokerPoolName() + "-b1";
        final String poolB2NameAdded = testStorage.getBrokerPoolName() + "-b2-added";
        final int brokerNodePoolReplicaCount = 2;

        LOGGER.info("Deploy 2 KafkaNodePools {}, {}, in {}", poolAName, poolB1Name, testStorage.getNamespaceName());
        resourceManager.createResourceWithWait(
            NodePoolsConverter.convertNodePoolsIfNeeded(
                KafkaNodePoolTemplates.controllerPoolPersistentStorage(testStorage.getNamespaceName(), testStorage.getControllerPoolName(), testStorage.getClusterName(), 3).build()
            )
        );
        resourceManager.createResourceWithWait(
            KafkaNodePoolTemplates.brokerPoolPersistentStorage(testStorage.getNamespaceName(), poolAName, testStorage.getClusterName(), 1).build(),
            KafkaNodePoolTemplates.brokerPoolPersistentStorage(testStorage.getNamespaceName(), poolB1Name, testStorage.getClusterName(), brokerNodePoolReplicaCount).build(),
            KafkaTemplates.kafkaPersistent(testStorage.getClusterName(), 1, 3)
                .editOrNewSpec()
                    .editKafka()
                        .addToConfig("auto.create.topics.enable", "false")  // topics replica count helps ensure there are enough brokers
                        .addToConfig("offsets.topic.replication.factor", "3") // as some brokers (2) will be removed, this topic should have more than '1' default replica
                    .endKafka()
                .endSpec()
                .build()
        );

        transmitMessagesWithNewTopicAndClean(testStorage, 3);

        LOGGER.info("Add additional KafkaNodePool:  {}/{}", testStorage.getNamespaceName(), poolB2NameAdded);
        resourceManager.createResourceWithWait(
            KafkaNodePoolTemplates.brokerPoolPersistentStorage(testStorage.getNamespaceName(), poolB2NameAdded, testStorage.getClusterName(), brokerNodePoolReplicaCount).build()
        );

        KafkaNodePoolUtils.waitForKafkaNodePoolPodsReady(testStorage, poolB2NameAdded, ProcessRoles.BROKER, brokerNodePoolReplicaCount);

        // replica count of this KafkaTopic will require that new brokers were correctly added into Kafka Cluster
        transmitMessagesWithNewTopicAndClean(testStorage, 5);

        LOGGER.info("Delete KafkaNodePool: {}/{} and wait for Kafka pods stability", testStorage.getNamespaceName(), poolB1Name);
        KafkaNodePoolUtils.deleteKafkaNodePoolWithPodSetAndWait(testStorage.getNamespaceName(), testStorage.getClusterName(), poolB1Name);
        PodUtils.waitUntilPodStabilityReplicasCount(testStorage.getNamespaceName(), KafkaResource.getStrimziPodSetName(testStorage.getClusterName(), poolB2NameAdded), brokerNodePoolReplicaCount);
        PodUtils.waitUntilPodStabilityReplicasCount(testStorage.getNamespaceName(), KafkaResource.getStrimziPodSetName(testStorage.getClusterName(), poolAName), 1);

        transmitMessagesWithNewTopicAndClean(testStorage, 2);
    }

    private void transmitMessagesWithNewTopicAndClean(TestStorage testStorage, int topicReplicas) {
        final String topicName = testStorage.getTopicName() + "-replicas-" + topicReplicas + "-" + hashStub(String.valueOf(new Random().nextInt(Integer.MAX_VALUE)));
        final KafkaTopic kafkaTopic = KafkaTopicTemplates.topic(testStorage.getClusterName(), topicName, 1, topicReplicas, testStorage.getNamespaceName()).build();
        resourceManager.createResourceWithWait(kafkaTopic);

        LOGGER.info("Transmit messages with Kafka {}/{} using topic {}", testStorage.getNamespaceName(), testStorage.getClusterName(), topicName);
        KafkaClients kafkaClients = ClientUtils.getInstantPlainClientBuilder(testStorage)
            .withTopicName(topicName)
            .build();
        resourceManager.createResourceWithWait(kafkaClients.producerStrimzi(), kafkaClients.consumerStrimzi());
        ClientUtils.waitForInstantClientSuccess(testStorage);

        // clean topic
        resourceManager.deleteResource(kafkaTopic);
        KafkaTopicUtils.waitForKafkaTopicDeletion(testStorage.getNamespaceName(), topicName);
    }

    @BeforeAll
    void setup() {
        assumeFalse(Environment.isOlmInstall() || Environment.isHelmInstall());
        assumeTrue(Environment.isKafkaNodePoolsEnabled());

        this.clusterOperator = this.clusterOperator.defaultInstallation()
            .createInstallation()
            .runInstallation();
    }
}