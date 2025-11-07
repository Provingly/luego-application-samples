# Sample Luego Applications and Provingly Server

## Requirements
- maven
- Java 21: set your JAVA_HOME so that it points to Java JDK21 or later
If you need to install Java 21, please download and install it from https://adoptium.net/temurin/releases/?arch=any&version=21&os=any

## Generating a Luego application project from scratch (not yet available)
- copy the empty skeleton zip
- unzip it

```
mvn exec:java -Dexec.mainClass="builder.Main" -Dexec.args="app generate"
```

## Compiling and running an application
Go to the starter app folder: 
```
cd applications/sample-app-starter
```

### Cleaning the Luego app
```
mvn exec:java@luego-compiler -Dexec.args="app clean"
```
### Compiling the Luego app

```
mvn exec:java@luego-compiler -Dexec.args="app compile"
```

The default log level of the compiler is Info. If you want the compiler to output more information, you can set the level to Trace as follows:
```
export LUEGOC_LOG_LEVEL="Trace"
```
You can switch back to the Info log level to get a clear output in the console.
```
export LUEGOC_LOG_LEVEL="Info"
```

If compilation is successful, the folder target/luego will contain the binary files for each functions/decision models and we will be ready to run Luego programs.

### Running exploratory tests
```
mvn exec:java@luego-runner
```

### Automated unit testing with JUnit
```
mvn test
```

Reuse test data from your exploratory tests in the automated tests.

### Packaging the Luego app
```
mvn exec:java@luego-compiler -Dexec.args="app package"
```

If packaging is successful, you should have a file called luego-starter-app.zip in the folder target/luego. We will be ready to deploy our application to a Provingly server.

### Deploying to Provingly Server
- copying and expanding an archive in the mounted volume used by the Docker container
- using the deployment APIs 


## The Luego programming language
Visit https://docs.provingly.io