package com.orangelit.javawebtemplate.infrastructure;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.orangelit.javawebtemplate.application.AuthenticationService;
import com.orangelit.javawebtemplate.domain.authentication.Session;

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
    Session session = authenticationService.register("Kevin", "Kinnebrew", "kevin.kinnebrew@gmail.com", "test",
        "0.0.0.0");
  }

}
