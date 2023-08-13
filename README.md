## How to run the project?

If you are first time running the project, you need to run the following commands
```bash
$ mvn clean
$ mvn compile
$ mvn package
$ mvn install
$ mvn exec:java
```

If you have already run the project before, you can run the following command
```bash
$ mvn -q clean compile exec:java
```

`-q` - quiet mode - It will not print the maven logs
