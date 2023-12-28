package com.beelog.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.beehive.annotations.Injectable;
import com.beehive.lib.service.Service;
import com.beelog.user.entity.UserEntity;
import com.beelog.user.service.UserEmailAlreadyExistException;
import com.beelog.user.service.UserService;
import com.beelog.user.service.UsernameAlreadyExistException;

import java.util.UUID;

@Injectable
public class AuthService extends Service {

  private final UserService userService = service(UserService.class);
  private final PasswordService passwordService = service(PasswordService.class);
  Algorithm algorithm = Algorithm.HMAC256("baeldung");

  public UserEntity register(String name, String username, String email, String password)
    throws UserEmailAlreadyExistException, UsernameAlreadyExistException {
    String passwordHash = passwordService.hash(password);

    return userService.create(name, username, email, passwordHash);
  }

  public String login(String username, String password) throws UserNotFoundException, InvalidCredentialsException {
    UserEntity user = userService.findByUsername(username);

    if (user == null) {
      throw new UserNotFoundException(username);
    }

    boolean isValid = passwordService.validate(password, user.getPasswordHash());

    if (!isValid) {
      throw new InvalidCredentialsException();
    }

    return JWT.create()
      .withClaim("username", username)
      .withClaim("email", user.getEmail())
      .withClaim("name", user.getName())
      .withJWTId(UUID.randomUUID().toString())
      .sign(algorithm);
  }

}
