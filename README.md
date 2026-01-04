# Build and Run Sample Luego Applications.

The goal of this repository is to help you getting started with programming in the Luego language by being able to look at real-world working examples.
The application samples aim at showing various level of complexity, various use cases in various industries.

## Description of the samples
This repositort contains the following application samples.

| Sample                 | Summary                    | Ingredients  |
| ---------------------  | -------------------------- | ------------ |
| luego-hello            | Basic app                  | Hello world, very simple functions and decision models             |
| luego-loyalty-points   | Business logic 101         | Modeling a business domain and some decision logic |
| luego-banking-offers   | Composing decisions        | Validation, Eligibility and Discount calculation |
| insurance-fraud (soon)       | Fraud patterns                 | Precise modeling of sophisticated fraud patterns   |
| car-tax-discount (soon)      | Eligibility for tax reductions | How to model sophisticated eligibility criteria        |

## Prerequisites to run the samples
- maven
- Java 21: set your JAVA_HOME so that it points to Java JDK21 or later
If you need to install Java 21, you can download and install it from https://adoptium.net/temurin/releases/?arch=any&version=21&os=any

## Compiling the Luego source code
Run the following commands to compile the source code of all the modules contained in the folder 'applications'. These commands will compile both the Luego source code (the business logic of your Luego applications) and some utility classes (like scenario runners and conversational exploratory tests) written in Java.
```
cd applications
mvn clean compile
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

If packaging is successful, you should have an archive called app.zip in the folder target/luego. We will be ready to deploy our application to a Provingly server.

### Deploying to the folder used by our Provingly Server
In a Provingly Server, deployable artefacts are organized in domains.
- A Provingly Server manages multiple domains identified by a unique name with the Provingly Server application repository.
- A domain contains multiple applications and each application is identified by a name that is unique within a given domain.
- A application contains multiple application versions. Applications are versioned using semantic versioning, which means an application full version is made of three numbers 'M.m.p', where M is the major version number, m the minor version number and p the patch number.

Here is an example:
- Pricing/DiscountCalculation/1.0.0
- Pricing/DiscountCalculation/1.0.1
- Pricing/DiscountCalculation/1.1.0
- Pricing/DiscountCalculation/1.1.1
- Pricing/DiscountCalculation/1.1.2
- Pricing/DiscountCalculation/2.0.0

'Pricing' is a domain that contains application 'DiscountCalculation'. Six different versions of the application 'DiscountCalculation' have been deployed. It is good practice to use the following convention:
- increment the patch version number for bug fixes
- increment the minor version number for functional changes that are backward compatible
- increment the major version number for non backward compatible changes.

Deploying an application will consist in copying an application archive in the right location in a Provingly Server by specifying:
- a domain name
- an application name
- the versioning scheme to be applied: we might either want to:
    - assign a specific version number to the application 
    - increment the major version number
    - increment the minor version number
    - increment the patch version number

There are several ways to deploy an application:
- manually copy and expand an archive in a volume that will be mounted by the Docker container
- use a command to deploy to a local directory used by a Provingly Server
- the recommended approach is to deploy using app deployment APIs provided by a Provingly Server

When calling an application, the calling application will be able to specify a full version, e.g. to make a call to  application `Pricing/DiscountCalculation/1.1.0` or a partial version, e.g. to make a call to application `Pricing/DiscountCalculation/1.1`. When a partial version is passed, the server will determine the most recent patch and will use it to serve the request.

### Start the Docker image to run the Provingly Server APIs
...

### Open your browser to ... and test APIs with SwaggerUI
...

## Generating a Luego application project from scratch (not yet available)
```
mvn luego:generateApp
```

or use the Luego app generation tool

## Check the version of the Luego compiler and runtime
After compiling an application, the folder target/luego will contain a file called build_info.yaml. Its content will indicate the version of the Luego language used by the compiler to generate binary code and the date of the build.
```
---
luego-version: 1.0.0-b49
build-date: 2025-12-17T22:07:13.246236
```


## Documentation of the Luego programming language and the Provingly technology
Visit https://docs.provingly.io