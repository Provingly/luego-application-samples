# Build and Run Sample Luego Applications.

## Requirements
- maven
- Java 21: set your JAVA_HOME so that it points to Java JDK21 or later
If you need to install Java 21, you can download and install it from https://adoptium.net/temurin/releases/?arch=any&version=21&os=any

## Compiling the Luego source code
Run the following commands to compile the source code of all the modules contained in the folder 'applications'. These commands will compile both the Luego source code (the business logic of your Luego applications) and some utility classes (like scenario runners and conversational exploratory tests) written in Java.
```
cd applications
mvn clean
mvn compile
```

When the compilation is successful for a given module, the folder target/luego will contain binary files for each function or decision model. We will then be ready to run Luego programs that use those functions and decision models.

If you compile the Luego source again after a modification of the source code, the old compiled binary files will be automatically cleaned.

### Running some scenarios
```
mvn exec:java@scenario-runner
```
This maven goal runs the Java program `runners.ScenarioRunner` located in src/main/java. You can look at the ScenarioRunner.java file to see how to integrate a Luego program into Java.

### Running exploratory tests
```
mvn exec:java@luego-exploratory-tests
```
This maven goal runs the Java program `runners.ExploratoryTestRunner` located in src/main/java. You can look at the ExploratoryTestRunner.java file to see how to configure an exploratory test for a function/decision model of your choice.

The exploratory test runner is a great way to quickly verify the behaviour of our Luego programs for various scenarios. The creation of scenario data is greatly facilitated by a conversational agent that will ask relevant questions to incrementally build an input payload to test a function or decision model.

We can then reuse the created input payloads in our automated regression tests.

### Automated unit testing with JUnit
```
mvn test
```
This maven goal runs automated tests defined with JUnit in src/test/java.

### Packaging the Luego app
```
mvn package
```

If packaging is successful, you should have an archive called luego-starter-app.zip in the folder target/luego. We will be ready to deploy our application to a Provingly server.

### Deploying to the folder used by our Provingly Server
- copying and expanding an archive in a volume that will be mounted by the Docker container

### Start the Docker image to run the Provingly Server APIs
...

### Open your browser to ... and test APIs with SwaggerUI
...

## Generating a Luego application project from scratch (not yet available)
- copy the empty skeleton zip
- unzip it
- adapt it

or use the Luego app generation tool

## Check the version of the Luego compiler and runtime
...


## Documentation of the Luego programming language and the Provingly technology
Visit https://docs.provingly.io