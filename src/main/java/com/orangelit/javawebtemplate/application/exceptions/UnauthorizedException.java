package com.orangelit.javawebtemplate.application.exceptions;

/**
 * @author kkinnebrew
 */
public class UnauthorizedException extends Exception {

  public UnauthorizedException(final String message) {
    super(message);
  }

  public UnauthorizedException() {}

}
