package infrastructure;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import application.AuthenticationService;
import domain.authentication.Session;

/**
 * @author kkinnebrew
 */
public class DBSetup {

  private final AuthenticationService authenticationService;

  @Inject
  public DBSetup(final AuthenticationService authenticationService) throws Exception {
    this.authenticationService = authenticationService;
    this.run();
  }

  @Transactional
  public void run() throws Exception {
    Session session1 = authenticationService.register("Kevin", "Kinnebrew", "kevin.kinnebrew@gmail.com", "test",
        "0.0.0.0");
    Session session2 = authenticationService.register("Mindy", "Cruz", "mindy.cruz@gmail.com", "test",
        "0.0.0.0");
  }

}
