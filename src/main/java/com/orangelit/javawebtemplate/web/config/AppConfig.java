package com.orangelit.javawebtemplate.web.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.sitebricks.SitebricksModule;
import com.orangelit.javawebtemplate.web.apis.*;

/**
 * @author kkinnebrew
 */
public class AppConfig extends GuiceServletContextListener {

  @Override
  public Injector getInjector() {
    return Guice.createInjector(new JpaPersistModule("mysql-persistence"), new SitebricksModule() {
      @Override
      protected void configureSitebricks() {
        scan(AuthenticationAPI.class.getPackage());
      }
    }, new AccountingModule());
  }

}