package com.beelog.user.service;

import com.beehive.annotations.Injectable;
import com.beehive.lib.Service.Service;
import com.beelog.user.entity.UserEntity;
import com.beelog.user.repository.UserRepository;

@Injectable
public class UserService extends Service {
  private final UserRepository userRepository = repository(UserRepository.class);

  public void create(String name, String username, String email, String passwordHash) {
    UserEntity user = new UserEntity();

    user.setName(name);
    user.setUsername(username);
    user.setEmail(email);
    user.setPasswordHash(passwordHash);

    userRepository.save(user);
  }

  public UserEntity findByUsername(String username) {
    return userRepository.findOne((cb, root) -> cb.equal(root.get("username"), username));
  }
}
