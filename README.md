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

## Contributing

Contributions are welcome! Please feel free to submit pull requests, create issues, or suggest improvements to the documentation or code.