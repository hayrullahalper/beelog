# Java Bean

**What is a Java Bean?**

A JavaBean is just a [standard](https://www.oracle.com/java/technologies/javase/javabeans-spec.html). It is a regular Java `class`, except it follows certain conventions:

- All properties in the class must be private (use **getters** and **setters** to access them)
- A public no-argument constructor
- Implements `Serializable` interface

Actually, `JavaBean` is a convention rather than a strict rule. It is a standard that we can follow to make our code more readable and maintainable.

**Why use Java Beans?**

Java Beans are used to encapsulate many objects into a single object (the bean), so that they can be passed around as a single bean object instead of as multiple individual objects.

**Example**

```java
public class Person implements Serializable {
    private String name;
    private int age;
    private String address;

    public Person() {
    }

    public Person(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
```

## What is Serializable interface?

The `Serializable` interface is a marker interface. It is used to indicate that a class can be serialized. It does not contain any methods or fields.

**What is serialization?**

Serialization is the process of converting an object into a stream of bytes. Deserialization is the reverse process of converting a stream of bytes into an object.

**Why do we need serialization?**

Serialization is used to save the state of an object so that it can be recreated later. For example, we can use serialization to save the state of a game character so that we can load it later.

_**TODO:** actually I don't know what is serialization. we will learn it later._

## Java Bean Validation

**What is Java Bean Validation?**

Java Bean Validation is a specification that allows us to validate Java Beans. It is a set of annotations that we can use to validate the properties of a Java Bean.

**Example**

```java
public class Person implements Serializable {
    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @Min(18)
    @Max(100)
    private int age;

    @NotNull
    @Size(min = 2, max = 100)
    private String address;

    public Person() {
    }

    public Person(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    // Getters and setters
}
```

**How to validate a Java Bean?**

We can use the `Validator` class to validate a Java Bean. The `Validator` class is part of the `javax.validation` package.

```java
public class Main {
    public static void main(String[] args) {
        Person person = new Person("John", 20, "123 org.hayrullah.Main St");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Person>> violations = validator.validate(person);

        for (ConstraintViolation<Person> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}
```

## References

- [Java Bean](https://www.baeldung.com/java-bean)
- [Javax Validation](https://www.baeldung.com/javax-validation)
- [What is a JavaBean exactly](https://stackoverflow.com/questions/3295496/what-is-a-javabean-exactly)