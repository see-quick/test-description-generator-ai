---

# JavadocTestGenerator

`JavadocTestGenerator` is a Java application designed to automatically generate `@TestDoc` and `@SuiteDoc` annotations for Java methods and classes based on predefined EBNF grammar and method signatures. This project leverages the JavaParser library to parse Java source code, the OpenAI API for generating content based on natural language processing, and ANTLR for custom grammar parsing.

## Features

- **Dynamic Annotation Generation**: Generates detailed Java annotations dynamically based on the method or class signature and custom grammar rules.
- **Integration with OpenAI API**: Utilizes OpenAI's powerful language models to generate human-like, contextually appropriate documentation comments.
- **Custom Grammar Parsing**: Leverages ANTLR to parse and interpret custom EBNF grammar rules defining the structure of `@TestDoc` and `@SuiteDoc` annotations.
- **Automated Documentation**: Facilitates the creation of structured documentation for both individual methods and test suites, improving code maintainability and readability.

## Prerequisites

- Java 11 or higher
- Maven or Gradle (for dependency management)
- Access to OpenAI API (API key required)
- ANTLR 4.8 or higher (for parsing custom grammar)

## Setup and Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-github/javadoc-test-generator.git
   cd javadoc-test-generator
   ```

2. **Install Dependencies**:
   Using Maven:
   ```bash
   mvn install
   ```

3. **Set Up OpenAI API Key**:
   Ensure you have an API key from OpenAI. Set it as an environment variable:
   ```bash
   export OPEN_AI_API_KEY='your_api_key_here'
   ```

4. **Run the Application**:
   ```bash
   java -jar target/javadoc-test-generator-1.0-SNAPSHOT.jar <input-file-path> <output-file-path>
   ```

## Usage

The `JavadocTestGenerator` takes two arguments:
- **Input File Path**: The path to the Java file that needs documentation.
- **Output File Path**: The path where the modified Java file with `@TestDoc` annotations will be saved.

## EBNF Grammar

The application uses Extended Backus-Naur Form (EBNF) to define the structure of the @TestDoc and @SuiteDoc annotations. This grammar is essential for ANTLR to parse and generate the annotations correctly based on the defined rules.
```plaintext
TestDocAnnotation ::= '@TestDoc' '(' TestDocBody ')'
TestDocBody ::= TestDocAttribute { ',' TestDocAttribute }
SuiteDocAnnotation ::= '@SuiteDoc' '(' SuiteDocBody ')'
SuiteDocBody ::= SuiteDocAttribute { ',' SuiteDocAttribute }
...
```

## Example

This section demonstrates how our Java application enhances test methods and classes with auto-generated documentation using the @TestDoc and @SuiteDoc annotations.
The examples below show a before-and-after scenario for a test method and a test class.
### Before

Initially, the test method might simply look like this:

```java
void testNPWhenOperatorIsInDifferentNamespaceThanOperand() {
    // method implementation
}
```

### After - Using @TestDoc

```java
@TestDoc(
     description = @Desc("Test verifying the behavior of the Cluster Operator when it is deployed in a different namespace than the Kafka operand."),
     contact = @Contact(name = "see-quick", email = "maros.orsak159@gmail.com"),
     steps = {
          @Step(value = "Assume the environment is not using Helm or OLM for installation", expected = "Condition is verified"),
          @Step(value = "Initialize test storage and define a secondary namespace", expected = "Test storage is set and secondary namespace is defined"),
          @Step(value = "Create and configure Cluster Operator environment variables", expected = "Cluster Operator environment variables are configured"),
          @Step(value = "Run the Cluster Operator installation with the specified namespaces", expected = "Cluster Operator is installed across specified namespaces"),
          @Step(value = "Edit the namespace to add custom labels", expected = "Namespace is successfully updated with new labels"),
          @Step(value = "Set the secondary namespace for cluster operations", expected = "Secondary namespace is set"),
          @Step(value = "Create resources for Kafka Node Pools", expected = "Kafka Node Pools are created with persistent storage"),
          @Step(value = "Create Kafka resources with metrics configuration", expected = "Kafka resources with metrics configuration are deployed"),
          @Step(value = "Verify network policies in the secondary namespace", expected = "Network policies are verified successfully"),
          @Step(value = "Change Kafka configuration and verify observed generation changes", expected = "Kafka configuration changes are applied and verified")
     },
     useCases = {
          @UseCase(id = "namespace-separation"),
          @UseCase(id = "cluster-operator-multi-namespace"),
          @UseCase(id = "kafka-deployment")
     },
     tags = {
          @TestTag(value = "namespace"),
          @TestTag(value = "kafka"),
          @TestTag(value = "cluster-operator")
     }
)
void testNPWhenOperatorIsInDifferentNamespaceThanOperand() {
     // method implementation
}
```

### After - Using `SuiteDoc`

```java
@SuiteDoc(
    description = @Desc("Suite handling all Kafka deployment tests."),
    contact = @Contact(name = "John Doe", email = "john.doe@example.com"),
    beforeTestSteps = {
        @Step(value = "Setup initial environment constraints", expected = "Environment setup complete"),
        ...
    },
    afterTestSteps = {
        @Step(value = "Clean up resources", expected = "Resources cleaned up"),
        ...
    },
    useCases = {
        @UseCase(id = "full-kafka-deployment"),
        ...
    },
    tags = {
        @TestTag(value = "suite"),
        ...
    }
)
public class KafkaDeploymentTests {
    // class implementation
}
```

## Generating Markdown Documentation

The annotations produced by this tool can be further transformed into structured Markdown documentation, facilitating 
easy integration into project wikis or documentation websites. 
This can be achieved using tools like the one available at [Test Metadata Generator](https://github.com/skodjob/test-metadata-generator), 
which formats Java annotations into readable Markdown format.

For instance, the annotation for the `testNPGenerationEnvironmentVariable` test method can be translated into the following Markdown format:
```markdown
## testNPGenerationEnvironmentVariable

**Description:** Test ensuring that no NetworkPolicies are generated by Strimzi when the STRIMZI_NETWORK_POLICY_GENERATION environment variable is set to false.

**Contact:** `see-quick <maros.orsak159@gmail.com>`

**Steps:**

| Step | Action | Result |
| - | - | - |
| 1. | Assume the test environment is not set for Helm or OLM installation | The test proceeds only if the assumption is true |
| 2. | Set the environment variable STRIMZI_NETWORK_POLICY_GENERATION to false | The environment variable is configured correctly |
| 3. | Install the Cluster Operator with the specified environment variable | The Cluster Operator is installed successfully |
| 4. | Create Kafka NodePool resource | Kafka NodePool resource is created successfully |
| 5. | Create Kafka resource with Cruise Control | Kafka resource with Cruise Control is created successfully |
| 6. | Create Kafka Connect resource | Kafka Connect resource is created successfully |
| 7. | Check for NetworkPolicies generated by Strimzi | No NetworkPolicies are generated |

**Use-cases:**

* `network-policy-generation-disabled`
* `cruise-control-validation`

**Tags:**

* `CRUISE_CONTROL`
* `network-policy`
```
This Markdown documentation provides a clear, organized, and easy-to-navigate presentation of the test details, 
enhancing the accessibility and usability of your project's documentation.

## Contributing

Contributions are welcome! Please feel free to submit pull requests, create issues, or suggest improvements to the documentation or code.