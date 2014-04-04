package application;

import application.exceptions.InvalidInputException;
import application.exceptions.NotFoundException;
import application.exceptions.UnauthorizedException;
import domain.authentication.Session;
import domain.authentication.User;

import java.util.List;

/**
 * @author kkinnebrew
 */
public interface AuthenticationService {

  Session register(String firstName, String lastName, String email, String password, String hostname)
      throws InvalidInputException, NotFoundException;

  String login(String email, String password, String hostname)
      throws InvalidInputException, NotFoundException, UnauthorizedException;

  Boolean validate(String token, String hostname)
      throws InvalidInputException, NotFoundException, UnauthorizedException;

  void logout(String token)
      throws NotFoundException;

  void disable(String email, String password)
      throws InvalidInputException, NotFoundException, UnauthorizedException;

  List<User> getUsers();

  List<User> searchUsers(String keyword);

  User getUser(String userId)
      throws NotFoundException;

  User getUserByEmail(String email)
      throws NotFoundException;

}