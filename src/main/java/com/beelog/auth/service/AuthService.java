package com.beelog.auth.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.beehive.annotations.Injectable;
import com.beehive.lib.service.Service;
import com.beelog.user.entity.UserEntity;
import com.beelog.user.service.UserNotFoundException;
import com.beelog.user.service.UserService;

@Injectable
public class AuthService extends Service {

  private final UserService userService = service(UserService.class);

  public void register(String name, String username, String email, String password) {
    userService.create(name, username, email, hashPassword(password));
  }

  public boolean login(String username, String password) {
    try {
      UserEntity user = userService.findByUsername(username);
      return verifyPassword(password, user.getPasswordHash());
    } catch (UserNotFoundException e) {
      return false;
    }
  }

  private String hashPassword(String password) {
    return BCrypt.withDefaults().hashToString(12, password.toCharArray());
  }

  private boolean verifyPassword(String password, String hash) {
    BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hash);
    return result.verified;
  }
}
