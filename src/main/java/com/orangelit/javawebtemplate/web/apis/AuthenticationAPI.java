package com.orangelit.javawebtemplate.web.apis;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.google.sitebricks.At;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Post;
import com.orangelit.javawebtemplate.application.AuthenticationService;
import com.orangelit.javawebtemplate.application.exceptions.InvalidInputException;
import com.orangelit.javawebtemplate.application.exceptions.NotFoundException;
import com.orangelit.javawebtemplate.application.exceptions.UnauthorizedException;
import com.orangelit.javawebtemplate.domain.authentication.Session;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kkinnebrew
 */
@At("/api/auth")
@Service
public class AuthenticationAPI {

  private final AuthenticationService authenticationService;
  private final HttpServletRequest httpServletRequest;

  /**
   * @param authenticationService
   */
  @Inject
  public AuthenticationAPI(final AuthenticationService authenticationService,
                           final HttpServletRequest httpServletRequest) {
    this.authenticationService = authenticationService;
    this.httpServletRequest = httpServletRequest;
  }

  @At("/register")
  @Post
  @Transactional
  public Reply register(Request request) {
    String firstName = request.param("firstName");
    String lastName = request.param("lastName");
    String email = request.param("email");
    String password = request.param("password");
    String confirm = request.param("confirm");
    String hostname = httpServletRequest.getRemoteHost();
    if (password != null && !password.equals(confirm)) {
      throw new IllegalArgumentException();
    }
    try {
      Session session = authenticationService.register(firstName, lastName, email, password, hostname);
      return Reply.with(session.getSessionId()).as(Json.class);
    } catch (InvalidInputException ex) {
      return Reply.with(ex.getMessage()).status(400);
    } catch (Exception ex) {
      return Reply.with("Error persisting user").status(400);
    }
  }

  @At("/login")
  @Post
  @Transactional
  public Reply login(Request request) {
    String email = request.param("email");
    String password = request.param("password");
    String hostname = httpServletRequest.getRemoteHost();
    try {
      String token = authenticationService.login(email, password, hostname);
      return Reply.with(token).as(Json.class);
    } catch (NotFoundException ex) {
      return Reply.with(ex.getMessage()).notFound();
    } catch (UnauthorizedException ex) {
      return Reply.with(ex.getMessage()).forbidden();
    } catch (InvalidInputException ex) {
      return Reply.with(ex.getMessage()).status(400);
    }
  }

  @At("/validate")
  @Post
  @Transactional
  public Reply validate(Request request) {
    String token = request.param("token");
    String hostname = httpServletRequest.getRemoteHost();
    try {
      Boolean isValid = authenticationService.validate(token, hostname);
      return Reply.with(isValid).as(Json.class);
    } catch (NotFoundException ex) {
      return Reply.with(ex.getMessage()).notFound();
    } catch (UnauthorizedException ex) {
      return Reply.with(ex.getMessage()).forbidden();
    } catch (InvalidInputException ex) {
      return Reply.with(ex.getMessage()).status(400);
    }
  }

  @At("/disable")
  @Post
  @Transactional
  public Reply disable(Request request) {
    String email = request.param("email");
    String password = request.param("password");
    try {
      authenticationService.disable(email, password);
      return Reply.saying().ok();
    } catch (NotFoundException ex) {
      return Reply.with(ex.getMessage()).notFound();
    } catch (UnauthorizedException ex) {
      return Reply.with(ex.getMessage()).forbidden();
    } catch (InvalidInputException ex) {
      return Reply.with(ex.getMessage()).status(400);
    }
  }

  @At("/logout")
  @Post
  @Transactional
  public Reply logout(Request request) {
    String token = request.param("token");
    try {
      authenticationService.logout(token);
      return Reply.saying().ok();
    } catch (NotFoundException ex) {
      return Reply.with(ex.getMessage()).notFound();
    }
  }

}
