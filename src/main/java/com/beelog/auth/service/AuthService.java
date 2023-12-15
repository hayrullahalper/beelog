package com.beelog.auth.service;

import com.beehive.lib.service.Service;
import com.beelog.user.entity.UserEntity;
import com.beehive.annotations.Injectable;
import com.beelog.user.service.UserService;
import com.beelog.user.service.UserEmailAlreadyExistException;
import com.beelog.user.service.UsernameAlreadyExistException;

@Injectable
public class AuthService extends Service {

  private final UserService userService = service(UserService.class);

  private final PasswordService passwordService = service(PasswordService.class);

  public UserEntity register(String name, String username, String email, String password)
    throws UserEmailAlreadyExistException, UsernameAlreadyExistException {
    String passwordHash = passwordService.hash(password);

    return userService.create(name, username, email, passwordHash);
  }

  public boolean login(String username, String password) throws UserNotFoundException {
    UserEntity user = userService.findByUsername(username);

    if (user == null) {
      throw new UserNotFoundException(username);
    }

    return passwordService.validate(password, user.getPasswordHash());
  }

}
