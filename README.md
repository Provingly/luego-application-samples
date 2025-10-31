# Sample applications using the Luego language and Provingly Server

## Requirements
- maven
- Java 21: set your JAVA_HOME so that it points to Java JDK21 or later
If you need to install Java 21, please download and install it from https://adoptium.net/temurin/releases/?arch=any&version=21&os=any

- install the provided Luego jar file into your local maven repository:
mvn install:install-file \
   -Dfile=./tools/jars/luego_devtools-assembly-0.1.5.jar \
   -DgroupId=provingly.io \
   -DartifactId=luego_devtools_3 \
   -Dversion=0.1.5 \
   -Dpackaging=jar \
   -DgeneratePom=true

## Generating a Luego application project from scratch
java -jar ../../tools/jars/luego_devtools-assembly-0.1.5.jar app generate


## Compiling and running an application
Go to the starter app folder: `cd applications/sample-app-starter`

### Cleaning 
java -jar ../../tools/jars/luego_devtools-assembly-0.1.5.jar app clean

### Compiling and packaging   
java -jar ../../tools/jars/luego_devtools-assembly-0.1.5.jar app compile

If compilation is succesfull, you should have a file called luego-starter-app.zip in the folder target/luego.

### Running
mvn exec:java -Dexec.mainClass="runner.Main"

### Unit testing

### Deploying to Provingly Server

## The Luego programming language
Visit https://docs.provingly.io