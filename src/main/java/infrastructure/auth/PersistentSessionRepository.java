package infrastructure.auth;

import com.google.inject.Inject;
import application.exceptions.NotFoundException;
import domain.authentication.Session;
import domain.authentication.SessionRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * @author kkinnebrew
 */
public class PersistentSessionRepository implements SessionRepository {

  private final EntityManager entityManager;

  /**
   * @param entityManager
   */
  @Inject
  public PersistentSessionRepository(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Session find(String sessionId) throws NotFoundException {
    Session session = entityManager.find(Session.class, sessionId);
    if (session == null) {
      throw new NotFoundException(Session.class, sessionId);
    }
    return session;
  }

  @Override
  public Session save(Session session) {
    entityManager.persist(session);
    return session;
  }

  @Override
  public void remove(Session session) {
    entityManager.remove(session);
  }

  @Override
  public void clear(String userId) {
    Query query = entityManager.createNativeQuery("delete from Sessions WHERE userId = ?");
    query.setParameter(1, userId);
    query.executeUpdate();
  }

}
