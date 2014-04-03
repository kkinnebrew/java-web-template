package com.orangelit.javawebtemplate.web.config;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;

/**
 * @author kkinnebrew
 */
@Singleton
public class PersistenceInitializer {

  @Inject
  public PersistenceInitializer(final PersistService service) {
    service.start();
  }

}