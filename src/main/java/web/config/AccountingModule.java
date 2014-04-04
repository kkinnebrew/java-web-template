package web.config;

import com.google.inject.AbstractModule;
import application.*;
import domain.authentication.SessionRepository;
import domain.authentication.UserRepository;
import infrastructure.DBSetup;
import infrastructure.auth.PersistentSessionRepository;
import infrastructure.auth.PersistentUserRepository;

/**
 * @author kkinnebrew
 */
public class AccountingModule extends AbstractModule {

  protected void configure() {

    bind(PersistenceInitializer.class).asEagerSingleton();
    bind(DBSetup.class).asEagerSingleton();

    bind(AuthenticationService.class).to(DefaultAuthenticationService.class);

    bind(UserRepository.class).to(PersistentUserRepository.class);
    bind(SessionRepository.class).to(PersistentSessionRepository.class);


  }

}
