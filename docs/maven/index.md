# What is Maven?

Maven is a build automation tool used primarily for Java projects. Maven can also be used to build and manage projects
written in C#, Ruby, Scala, and other languages. The Maven project is hosted by the Apache Software Foundation, where it
was formerly part of the Jakarta Project.

Maven addresses two aspects of building software: first, it describes how software is built, and second, it describes
its dependencies.

Also, **it can be used as a version control system**.

In theory, our code repository should be **ide-independent**. This is provided by Maven.

`src/main/java` is the directory for source codes.

`src/test/java` is the directory for test codes.

## How to install Maven?

first of all, you should have java installed on your computer.

1. Download Maven from [here](https://maven.apache.org/download.cgi).
2. Extract the archive to your desired location.
3. Add the `bin` directory of the created directory apache-maven-3.6.3 to the `PATH` environment variable.
4. Confirm with `mvn -v` in a new shell. The result should look similar to this:

these steps are not enough for you, you can look at [here](https://www.baeldung.com/install-maven-on-windows-linux-mac).

```bash
$ mvn -v
Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: /opt/apache-maven-3.6.3
Java version: 1.8.0_131, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.12.6", arch: "x86_64", family: "mac"
```

something like this should be printed on your terminal.

## How to create a Maven project?

```bash
$ mvn archetype:generate -D\
    groupId=org.deneme -D\
    artifactId=hello-world -D\
    archetypeArtifactId=maven-archetype-quickstart -D\
    interactiveMode=false
```

If you leave groupId, artifactId and interactiveMode blank, it will ask you.

Some useful links:

- [How to create a maven project with Intellij Idea](https://www.geeksforgeeks.org/how-to-create-a-maven-project-in-intellij-idea/)
- [Introduction to archetypes: What is archetype?](https://maven.apache.org/guides/introduction/introduction-to-archetypes.html)

## Maven Lifecycle and Basic Maven Commands

The default Maven lifecycle consists of the following phases:

1. **validate**: validate the project is correct and all necessary information is available
2. **compile**: compile the source code of the project
3. **test**: test the compiled source code using a suitable unit testing framework. These tests should not require the
   code be packaged or deployed
4. **package**: take the compiled code and package it in its distributable format, such as a JAR.
5. **integration-test**: process and deploy the package if necessary into an environment where integration tests can be
   run
6. **verify**: run any checks to verify the package is valid and meets quality criteria
7. **install**: install the package into the local repository, for use as a dependency in other projects locally
8. **deploy**: done in the build environment, copies the final package to the remote repository for sharing with other
   developers and projects.

|                                      ![Maven Lifecycle](./maven-lifecycle.png)                                       |
|:--------------------------------------------------------------------------------------------------------------------:|
| _[8 Phases of the Default Maven Lifecycle](https://www.geeksforgeeks.org/maven-lifecycle-and-basic-maven-commands/)_ |

### Basic Maven Commands

|         Command          |                                             Description                                             |
|:------------------------:|:---------------------------------------------------------------------------------------------------:|
|      `mvn compile`       |                              Compiles the source code of the project.                               |
|        `mvn test`        |      Runs the tests against the compiled source code using a suitable unit testing framework.       |
|      `mvn package`       |         Takes the compiled code and packages it in its distributable format, such as a JAR.         |
|      `mvn install`       | Installs the package into the local repository, for use as a dependency in other projects locally.  |
|       `mvn clean`        |                        Cleans the project by deleting the target directory.                         |
|       `mvn deploy`       |  Copies the final package to the remote repository for sharing with other developers and projects.  |
|    `mvn test-compile`    |                    Compiles the test source code into the test target directory.                    |
|        `mvn site`        |                           Generates site documentation for this project.                            |
|  `mvn dependency:tree`   |                           Displays the dependency tree for this project.                            |
| `mvn archetype:generate` | Generates a new project from an archetype, or an artifact from a given group, artifact and version. |

Note: You can run the `main` method with the `mvn exec:java` command with
the [`exec-maven-plugin`](https://www.mojohaus.org/exec-maven-plugin/) I added to the project.

Generally when we run any of the above commands, we add the `mvn clean` step so that the target folder generated from
the previous build is removed before running a newer build. This is how the command would look on integrating the clean
step with install phase:

```bash
$ mvn clean install
```

Similarly, if we want to run the step in debug mode for more detailed build information and logs, we will add `-X` to
the actual command. Hence, the install step with debug mode on will have the following command:

```bash
$ mvn -X install
```

Consider a scenario where we do not want to run the tests while packaging or installing the Java project. In this case,
we use `-DskipTests` along with the actual command. If we need to run the install step by skipping the tests associated
with the project, the command would be:

```bash
$ mvn install -DskipTests
```

## What is `pom.xml`?

A Project Object Model or POM is the fundamental unit of work in Maven. It is an `XML` file that contains information
about the project and configuration details used by Maven to build the project. It contains default values for most
projects. Examples for this is the build directory, which is target; the source directory, which is `src/main/java`; the
test source directory, which is `src/test/java`; and so on. When executing a task or goal, Maven looks for the POM in
the current directory. It reads the POM, gets the needed configuration information, then executes the goal.

## Project Object Model (POM) explained

The minimal POM is as follows:

- `project`: The root element of the POM.
- `modelVersion`: POM model version (always 4.0.0).
- `groupId`: Group or organization that the project belongs to. Often expressed as an inverted domain name.
- `artifactId`: Name to be given to the project's library artifact (for example, the name of its JAR or WAR file).
- `version`: Version of the project that is being built.

Here is an example of a minimal POM for a project that produces a library called `my-app`:

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.mycompany.app</groupId>
    <artifactId>my-app</artifactId>
    <version>1.0-SNAPSHOT</version>
</project>
```

A POM requires that its `groupId`, `artifactId`, and `version` be configured. These three values form the project's
fully qualified artifact name. For example, the above POM references a project with a fully qualified artifact name
of `com.mycompany.app:my-app:1.0-SNAPSHOT`.

### POM Best Practices

- The `groupId` is meant to identify the project's group uniquely across all projects. For example, all core Maven
  artifacts use `org.apache.maven` as their `groupId`. It is recommended to use a qualified name (for
  example, `org.mycompany.myproject`) to start your groupId to prevent it from conflicting with future additions to the
  central repository.
- The `artifactId` is meant to identify the artifact uniquely among your projects. For example, if you were creating a
  library project, this `artifactId` would be used as the name of the directory containing the project sources, the name
  of the project's default JAR file (e.g., `my-app-1.0.jar`, where the default final name
  is `${artifactId}-${version}.jar`), and the default prefix for the names of the artifacts generated by the project in
  its target directory.
- The `version` is meant to indicate the version of the artifact generated by the project. Maven goes a long way to help
  you with version management of your project: it encourages the use of version control systems for your source code,
  and has a number of options and features to help you manage, label, and release your project's artifacts.
- The `packaging` is meant to identify the packaging used by the project (e.g., JAR, WAR, EAR, etc.). While `optional`,
  it usually makes sense to specify `packaging` for your project, otherwise, the default `jar` value is used, so you
  would only need to specify `packaging` if you are creating something other than a JAR file. (We will generally
  use `war` for web applications.)
- The `name` is meant to be a human-readable moniker for the project. While `optional`, it is a good practice to supply
  this value, as it is the default way for humans to refer to your project. For example, Maven's default `install`
  directory is named `apache-maven`, which is the artifactId of the project.
- The `description` is meant to provide a human-readable description of the project. `optional`
- The `url` is meant to specify the project's home page. `optional`
- The `inceptionYear` is meant to specify the year when the project was started. `optional`
- The `licenses` is meant to list all of the licenses for this project. `optional`
- The `organization` is meant to specify the organization that produces this project. `optional`
- The `developers` is meant to list all of the developers who have contributed to this project. `optional`
- The `contributors` is meant to list all of the contributors who have contributed to this project. `optional`
- The `mailingLists` is meant to list all of the mailing lists for this project. `optional`

### Project Interpolation and Variable Reference

Maven supports the use of tokens to dynamically alter the POM at build time. These tokens, denoted by the `${...}`
delimiters, can come from the system properties, your project properties, from your filter resources, and from the
command line. For example, you can set the `groupId`, `artifactId`, and `version` of your project via the command line:

```bash
$ mvn -DgroupId=com.mycompany.app -DartifactId=my-app -Dversion=1.0-SNAPSHOT ...
```

You can then use these values in your POM as follows:

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}</artifactId>
    <version>${version}</version>
</project>
```

For example, in our current pom.xml file, `${project.basedir}` is used as an example.

## What is a dependency?

A dependency is a JAR file required for development. Maven downloads all the dependencies defined in the POM file from
the Maven repository and copies them to the local system. The dependencies are defined in the `pom.xml` file using
the `<dependencies>` tag.

### How to add a dependency?

To add a dependency, you need to add the `<dependencies>` tag in the `pom.xml` file. The `<dependencies>` tag contains a
list of `<dependency>` tags, each of which describes a dependency.

```xml
<dependencies>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <version>3.0</version>
    </dependency>
</dependencies>
```

## What is a plugin?

A plugin is a collection of one or more goals. A goal is a specific task used in Maven. For example, the `compile` goal
is used to compile the Java source files, the `test` goal is used to run the unit tests of the project, and
the `install` goal is used to install the project's artifacts into the local repository.

For example, the `maven-exec-plugin` is used to execute the main class of a Java application in the project.
The `maven-compiler-plugin` is used to compile the Java source code of the project. The `maven-surefire-plugin` is used
to run the unit tests of the project.

### How to add a plugin?

To add a plugin, you need to add the `<plugins>` tag in the `pom.xml` file. The `<plugins>` tag contains a list
of `<plugin>` tags, each of which describes a plugin.

```xml
<plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
    </plugin>
</plugins>
```
