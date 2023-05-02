package org.hayrullah.person;

import org.hayrullah.annotations.NotNull;

import java.io.Serializable;

public class Person implements Serializable {
  @NotNull
  private String name;

  @NotNull
  private int age;

  @NotNull
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