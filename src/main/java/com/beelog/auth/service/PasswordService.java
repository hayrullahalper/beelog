package com.beelog.auth.service;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.beehive.annotations.Injectable;
import com.beehive.lib.service.Service;

@Injectable
public class PasswordService extends Service {
  private final int SALT = 12;

  public String hash(String password) {
    return BCrypt.withDefaults().hashToString(SALT, password.toCharArray());
  }

  public boolean validate(String password, String hash) {
    BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hash);
    return result.verified;
  }
}
