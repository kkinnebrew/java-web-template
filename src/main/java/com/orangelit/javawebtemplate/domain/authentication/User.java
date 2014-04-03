package com.orangelit.javawebtemplate.domain.authentication;

import com.orangelit.javawebtemplate.application.exceptions.InvalidInputException;
import com.orangelit.javawebtemplate.common.Validate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.Date;

/**
 * @author kkinnebrew
 */
@Entity
@Table(name = "Users")
public class User {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String userId;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private Boolean isActive;

  @Column(nullable = false)
  private Boolean isAdmin;

  @Column(nullable = true)
  private Date created;

  @Column(nullable = true)
  private Date updated;

  public User() {}

  /**
   * @param firstName
   * @param lastName
   * @param email
   * @param password
   * @param isActive
   * @param isAdmin
   * @throws InvalidInputException
   */
  public User(String firstName, String lastName, String email, String password, Boolean isActive, Boolean isAdmin)
      throws InvalidInputException {

    Validate.notNullOrWhitespace(firstName, "First name is required");
    Validate.notNullOrWhitespace(lastName, "Last name is required");
    Validate.notNullOrWhitespace(email, "Email is required");
    Validate.notNullOrWhitespace(password, "Password is required");
    Validate.notNull(isActive, "Is active is required");
    Validate.notNull(isAdmin, "Is admin is required");

    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.isActive = isActive;
    this.isAdmin = isAdmin;

    try {
      this.password = PasswordHash.createHash(password);
    } catch (Exception ex) {
      throw new InvalidInputException("Invalid password");
    }

  }

  public String getUserId() {
    return userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) throws InvalidInputException {
    Validate.notNullOrWhitespace(firstName, "First name is required");
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFullName() {
    return MessageFormat.format("{0} {1}", getFirstName(), getLastName());
  }

  public void setLastName(String lastName) throws InvalidInputException {
    Validate.notNullOrWhitespace(lastName, "Last name is required");
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) throws InvalidInputException {
    Validate.notNullOrWhitespace(email, "Email is required");
    this.email = email;
  }

  public void setPassword(String password) throws InvalidInputException {
    Validate.notNullOrWhitespace(password, "Password is required");
    try {
      this.password = PasswordHash.createHash(password);
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getMessage());
    }
  }

  public Boolean IsActive() {
    return isActive;
  }

  public void setActive(Boolean isActive) throws InvalidInputException {
    Validate.notNull(isActive, "Is active is required");
    this.isActive = isActive;
  }

  public Boolean isValid(String password) {
    try {
      Boolean valid = PasswordHash.validatePassword(password, this.password);
      return valid;
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getMessage());
    }
    return false;
  }

  public Boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(Boolean isAdmin) throws InvalidInputException {
    Validate.notNull(isAdmin, "Is admin is required");
    this.isAdmin = isAdmin;
  }

  @PrePersist
  private void onCreate() {
    created = new Date();
  }

  @PreUpdate
  private void onUpdate() {
    updated = new Date();
  }

}