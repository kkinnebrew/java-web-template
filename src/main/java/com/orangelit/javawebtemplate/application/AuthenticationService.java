package com.orangelit.javawebtemplate.application;

import com.orangelit.javawebtemplate.application.exceptions.InvalidInputException;
import com.orangelit.javawebtemplate.application.exceptions.NotFoundException;
import com.orangelit.javawebtemplate.application.exceptions.UnauthorizedException;
import com.orangelit.javawebtemplate.domain.authentication.Session;
import com.orangelit.javawebtemplate.domain.authentication.User;

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