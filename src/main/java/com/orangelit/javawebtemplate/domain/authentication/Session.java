package com.orangelit.javawebtemplate.domain.authentication;

import com.orangelit.javawebtemplate.application.exceptions.InvalidInputException;
import com.orangelit.javawebtemplate.common.Validate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @author kkinnebrew
 */
@Entity
@Table(name = "Sessions")
public class Session {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String sessionId;

  @ManyToOne
  @JoinColumn(name = "userId")
  private User user;

  @Column(nullable = false)
  private String hostname;

  @Column(nullable = false)
  private Date expires;

  @Column(nullable = true)
  private Date created;

  @Column(nullable = true)
  private Date updated;

  public Session() {}

  /**
   * @param user
   * @param hostname
   */
  public Session(User user, String hostname) throws InvalidInputException {

    Validate.notNull(user, "User is required");
    Validate.notNullOrWhitespace(hostname, "Hostname is required");

    this.user = user;
    this.hostname = hostname;

    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MINUTE, 30);

    this.expires = calendar.getTime();

  }

  public String getSessionId() {
    return sessionId;
  }

  public User getUser() {
    return user;
  }

  public String getHostname() {
    return hostname;
  }

  public Boolean isExpired() {
    return (new Date()).compareTo(expires) > 0;
  }

  public void updateExpiration() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MINUTE, 30);
    this.expires = calendar.getTime();
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