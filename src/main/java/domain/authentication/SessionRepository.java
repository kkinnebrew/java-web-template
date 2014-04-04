package domain.authentication;

import application.exceptions.NotFoundException;

/**
 * @author kkinnebrew
 */
public interface SessionRepository {

  Session find(String sessionId) throws NotFoundException;

  Session save(Session session);

  void remove(Session session);

  void clear(String userId);

}