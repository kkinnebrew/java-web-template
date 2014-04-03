package com.orangelit.javawebtemplate.web.config;

import com.google.inject.AbstractModule;
import com.orangelit.javawebtemplate.application.*;
import com.orangelit.javawebtemplate.domain.authentication.SessionRepository;
import com.orangelit.javawebtemplate.domain.authentication.UserRepository;
import com.orangelit.javawebtemplate.infrastructure.DBSetup;
import com.orangelit.javawebtemplate.infrastructure.auth.PersistentSessionRepository;
import com.orangelit.javawebtemplate.infrastructure.auth.PersistentUserRepository;

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
