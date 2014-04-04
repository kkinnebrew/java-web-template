package application;

import com.google.inject.Inject;
import application.exceptions.InvalidInputException;
import application.exceptions.NotFoundException;
import application.exceptions.UnauthorizedException;
import common.Validate;
import domain.authentication.*;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkinnebrew
 */
public class DefaultAuthenticationService implements AuthenticationService {

  private final SessionRepository sessionRepository;
  private final UserRepository userRepository;

  /**
   * @param sessionRepository
   * @param userRepository
   */
  @Inject
  public DefaultAuthenticationService(final SessionRepository sessionRepository,
                                      final UserRepository userRepository) {
    this.sessionRepository = sessionRepository;
    this.userRepository = userRepository;
  }

  @Override
  public Session register(String firstName, String lastName, String email, String password, String hostname)
      throws InvalidInputException, NotFoundException {
    User user = new User(firstName, lastName, email, password, true, false);
    Session session = new Session(user, hostname);
    userRepository.save(user);
    sessionRepository.save(session);
    return session;
  }

  @Override
  public String login(String email, String password, String hostname)
      throws InvalidInputException, NotFoundException, UnauthorizedException {
    Validate.notNullOrWhitespace(password, "Password is required");
    User user = userRepository.findByEmail(email);
    if (!user.isValid(password)) {
      throw new UnauthorizedException("Invalid credentials");
    }
    Session session = new Session(user, hostname);
    sessionRepository.save(session);
    return session.getSessionId();
  }

  @Override
  public Boolean validate(String token, String hostname)
      throws InvalidInputException, NotFoundException, UnauthorizedException {
    Session session = sessionRepository.find(token);
    if (session.isExpired() || !session.getHostname().equals(hostname)) {
      sessionRepository.remove(session);
      throw new UnauthorizedException("Session has expired");
    }
    session.updateExpiration();
    sessionRepository.save(session);
    return true;
  }

  @Override
  public void logout(String token) throws NotFoundException {
    Session session = sessionRepository.find(token);
    sessionRepository.remove(session);
  }

  @Override
  public void disable(String email, String password)
      throws InvalidInputException, NotFoundException, UnauthorizedException {
    User user = userRepository.findByEmail(email);
    if (!user.isValid(password)) {
      throw new UnauthorizedException("Invalid credentials");
    }
    user.setActive(false);
    userRepository.save(user);
    sessionRepository.clear(user.getUserId());
  }

  @Override
  public List<User> getUsers() {
    return userRepository.select();
  }

  @Override
  public List<User> searchUsers(String keyword) {
    List<User> users = new ArrayList<>();
    if (keyword != null) {
      for (User user : userRepository.select()) {
        if (StringUtils.containsIgnoreCase(user.getEmail(), keyword)
            || StringUtils.containsIgnoreCase(user.getFullName(), keyword)) {
          users.add(user);
        }
      }
    } else {
      users = userRepository.select();
    }
    return users;
  }

  @Override
  public User getUser(String userId) throws NotFoundException {
    return userRepository.find(userId);
  }

  @Override
  public User getUserByEmail(String email) throws NotFoundException {
    return userRepository.findByEmail(email);
  }

}