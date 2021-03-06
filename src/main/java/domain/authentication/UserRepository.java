package domain.authentication;

import application.exceptions.NotFoundException;

import java.util.List;

/**
 * @author kkinnebrew
 */
public interface UserRepository {

  List<User> select();

  List<User> search(String keyword);

  User find(String userId) throws NotFoundException;

  User findByEmail(String email) throws NotFoundException;

  User save(User user);

  void remove(User user);

}
