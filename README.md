# Sample Luego Applications and Provingly Server

## Requirements
- maven
- Java 21: set your JAVA_HOME so that it points to Java JDK21 or later
If you need to install Java 21, please download and install it from https://adoptium.net/temurin/releases/?arch=any&version=21&os=any

- install the provided Luego jar file into your local maven repository:
```
mvn install:install-file \
   -Dfile=./tools/jars/luego_devtools-assembly-0.1.5.jar \
   -DgroupId=provingly.io \
   -DartifactId=luego_devtools_3 \
   -Dversion=0.1.5 \
   -Dpackaging=jar \
   -DgeneratePom=true
```

## Generating a Luego application project from scratch (not yet available)
```
java -jar ../../tools/jars/luego_devtools-assembly-0.1.5.jar app generate
```

## Compiling and running an application
Go to the starter app folder: 
```
cd applications/sample-app-starter
```

### Cleaning 
```
java -jar ../../tools/jars/luego_devtools-assembly-0.1.5.jar app clean
```
### Compiling and packaging   
```
java -jar ../../tools/jars/luego_devtools-assembly-0.1.5.jar app compile
```

If compilation is successful, the folder target/luego will contain the binary files for each functions/decision models and we will be ready to run Luego programs.

### Running exploratory tests
```
mvn exec:java -Dexec.mainClass="runner.Main"
```

### Automated unit testing with JUnit
```
mvn test
```

Reuse test data from your exploratory tests in the automated tests.

### Packaging   
```
java -jar ../../tools/jars/luego_devtools-assembly-0.1.5.jar app package
```

If packaging is successful, you should have a file called luego-starter-app.zip in the folder target/luego. We will be ready to deploy our application to a Provingly server.

### Deploying to Provingly Server
- copying and expanding an archive in the mounted volume used by the Docker container
- using the deployment APIs 


## The Luego programming language
Visit https://docs.provingly.io