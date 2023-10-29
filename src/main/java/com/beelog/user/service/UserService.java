package com.beelog.user.service;

import com.beehive.annotations.Injectable;
import com.beehive.lib.service.Service;
import com.beelog.user.entity.UserEntity;
import com.beelog.user.repository.UserRepository;
import jakarta.persistence.NoResultException;

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

  public UserEntity findByUsername(String username) throws UserNotFoundException {
    try {
      return userRepository.findOne((cb, root) -> cb.equal(root.get("username"), username));
    } catch (NoResultException e) {
      throw new UserNotFoundException("username", username);
    }
  }
}
