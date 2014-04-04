package infrastructure.auth;

import com.google.inject.Inject;
import application.exceptions.NotFoundException;
import domain.authentication.User;
import domain.authentication.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

/**
 * @author kkinnebrew
 */
public class PersistentUserRepository implements UserRepository {

  private final EntityManager entityManager;

  /**
   * @param entityManager
   */
  @Inject
  public PersistentUserRepository(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<User> select() {
    Query query = entityManager.createNativeQuery("select * from Users", User.class);
    List results = query.getResultList();
    List<User> users = new LinkedList<>();
    for (Object entity : results) {
      users.add((User) entity);
    }
    return users;
  }

  @Override
  public List<User> search(final String keyword) {
    Query query = entityManager.createNativeQuery("select * from Users WHERE email LIKE %?% OR firstName LIKE ?% OR lastName LIKE ?% LIMIT 5", User.class);
    query.setParameter((int)1, keyword);
    query.setParameter((int)2, keyword);
    query.setParameter((int)3, keyword);
    List results = query.getResultList();
    List<User> users = new LinkedList<>();
    for (Object entity : results) {
      users.add((User) entity);
    }
    return users;
  }

  @Override
  public User find(String userId) throws NotFoundException {
    User user = entityManager.find(User.class, userId);
    if (user == null) {
      throw new NotFoundException(User.class, userId);
    }
    return user;
  }

  @Override
  public User findByEmail(String email) throws NotFoundException {
    Query query = entityManager.createNativeQuery("select * from Users WHERE email = ? LIMIT 1", User.class);
    query.setParameter(1, email);
    User user = (User) query.getSingleResult();
    if (user == null) {
      throw new NotFoundException(User.class, email);
    }
    return user;
  }

  @Override
  public User save(User user) {
    entityManager.persist(user);
    return user;
  }

  @Override
  public void remove(User user) {
    entityManager.remove(user);
  }

}