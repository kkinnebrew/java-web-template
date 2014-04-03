package com.orangelit.javawebtemplate.domain.authentication;

import com.orangelit.javawebtemplate.application.exceptions.NotFoundException;

import java.util.List;

/**
 * @author kkinnebrew
 */
public interface UserRepository {

  List<User> select();

  User find(String userId) throws NotFoundException;

  User findByEmail(String email) throws NotFoundException;

  User save(User user);

  void remove(User user);

}
