package com.beelog.auth.controller;

import com.beehive.lib.controller.Controller;
import com.beelog.auth.service.AuthService;
import com.beelog.auth.service.InvalidCredentialsException;
import com.beelog.auth.service.UserNotFoundException;
import com.beelog.user.entity.UserEntity;
import com.beelog.user.service.UserEmailAlreadyExistException;
import com.beelog.user.service.UsernameAlreadyExistException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.validation.ConstraintViolation;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/auth")
public class AuthController extends Controller {

  private final AuthService authService = service(AuthService.class);

  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response login(String req) {
    LoginRequest request = new Gson().fromJson(req, LoginRequest.class);
    Set<ConstraintViolation<LoginRequest>> violations = validate(request);

    if (!violations.isEmpty()) {
      JsonObject errors = new JsonObject();

      for (ConstraintViolation<LoginRequest> violation : violations) {
        errors.addProperty(violation.getPropertyPath().toString(), violation.getMessage());
      }

      return Response
        .status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON)
        .entity(errors.toString())
        .build();
    }

    try {
      String token = authService.login(request.username, request.password);

      return Response
        .status(Response.Status.OK)
        .type(MediaType.APPLICATION_JSON)
        .entity("{ \"token\": \"" + token + "\" }")
        .build();
    } catch (UserNotFoundException | InvalidCredentialsException e) {
      return Response
        .status(Response.Status.UNAUTHORIZED)
        .type(MediaType.APPLICATION_JSON)
        .entity("{ \"error\": \"" + e.getMessage() + "\" }")
        .build();
    }
  }

  @POST
  @Path("/register")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response register(String req) {
    RegisterRequest request = new Gson().fromJson(req, RegisterRequest.class);
    Set<ConstraintViolation<RegisterRequest>> violations = validate(request);

    if (!violations.isEmpty()) {
      JsonObject errors = new JsonObject();

      for (ConstraintViolation<RegisterRequest> violation : violations) {
        errors.addProperty(violation.getPropertyPath().toString(), violation.getMessage());
      }

      return Response
        .status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON)
        .entity(errors.toString())
        .build();
    }

    try {
      UserEntity user = authService.register(request.name, request.username, request.email, request.password);

      return Response
        .status(Response.Status.OK)
        .type(MediaType.APPLICATION_JSON)
        .entity(request)
        .build();

    } catch (UserEmailAlreadyExistException e) {

      return Response
        .status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON)
        .entity("{ \"username\": \"User email already exist: " + request.email + "\" }")
        .build();

    } catch (UsernameAlreadyExistException e) {

      return Response
        .status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON)
        .entity("{ \"username\": \"Username already exist: " + request.username + "\" }")
        .build();

    }

  }
}
