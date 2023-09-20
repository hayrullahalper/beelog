package com.playground;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "name")
  private String name;

  public User() {
    super();
  }

  public User(String username, String email, String name) {
    super();

    this.username = username;
    this.email = email;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
      return "User [id=" + id + ", username=" + username + ", email=" + email + ", name=" + name + "]";
  }
}
