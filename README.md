---

# JavadocTestGenerator

`JavadocTestGenerator` is a Java application designed to automatically generate `@TestDoc` annotations for Java methods based on predefined EBNF grammar and method signatures. This project leverages the JavaParser library to parse Java source code, the OpenAI API for generating content based on natural language processing, and ANTLR for custom grammar parsing.

## Features

- **Dynamic Annotation Generation**: Generate detailed Java annotations dynamically based on the method signature and custom grammar rules.
- **Integration with OpenAI API**: Utilizes OpenAI's powerful language models to generate human-like, contextually appropriate documentation comments.
- **Custom Grammar Parsing**: Leverages ANTLR to parse and interpret custom EBNF grammar rules defining the structure of `@TestDoc` annotations.
- **Automated Documentation**: Facilitates the creation of structured documentation, improving code maintainability and readability.

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

The application uses Extended Backus-Naur Form (EBNF) to define the structure of the `@TestDoc` annotations. This grammar is essential for the ANTLR to parse and generate the annotations correctly based on the defined rules.

```plaintext
TestDocAnnotation ::= '@TestDoc' '(' TestDocBody ')'
TestDocBody ::= TestDocAttribute { ',' TestDocAttribute }
...
```

## Example

This section demonstrates how our Java application enhances test methods with auto-generated documentation using the @TestDoc annotation. 
The example below shows a before-and-after scenario for a test method in the NetworkPoliciesST class.

### Before

Initially, the test method might simply look like this:

```java
void testNPWhenOperatorIsInDifferentNamespaceThanOperand() {
    // method implementation
}
```

### After

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

## Contributing

Contributions are welcome! Please feel free to submit pull requests, create issues, or suggest improvements to the documentation or code.