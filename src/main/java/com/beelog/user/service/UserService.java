package com.beelog.user.service;

import com.beehive.annotations.Injectable;
import com.beehive.lib.service.Service;
import com.beelog.user.entity.UserEntity;
import com.beelog.user.repository.UserRepository;
import jakarta.persistence.NoResultException;

@Injectable
public class UserService extends Service {
  private final UserRepository userRepository = repository(UserRepository.class);

  public UserEntity create(String name, String username, String email, String passwordHash)
    throws UsernameAlreadyExistException, UserEmailAlreadyExistException {
    if (findByEmail(email) != null) {
      throw new UserEmailAlreadyExistException();
    }

    if (findByUsername(username) != null) {
      throw new UsernameAlreadyExistException();
    }

    UserEntity user = new UserEntity();

    user.setName(name);
    user.setUsername(username);
    user.setEmail(email);
    user.setPasswordHash(passwordHash);

    userRepository.save(user);

    return user;
  }

  public UserEntity findByUsername(String username) {
    try {
      return userRepository.findOne((cb, root) -> cb.equal(root.get("username"), username));
    } catch (NoResultException e) {
      return null;
    }
  }

  public UserEntity findByEmail(String email) {
    try {
      return userRepository.findOne((cb, root) -> cb.equal(root.get("email"), email));
    } catch (NoResultException e) {
      return null;
    }
  }

}
