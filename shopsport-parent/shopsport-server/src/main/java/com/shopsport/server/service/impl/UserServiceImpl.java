package com.shopsport.server.service.impl;

import com.shopsport.common.entity.User;
import com.shopsport.server.exception.user.UserNotFoundException;
import com.shopsport.server.repository.CustomerRepository;
import com.shopsport.server.repository.UserRepository;
import com.shopsport.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static com.shopsport.server.constant.CommonConstants.UserConstants.USER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final CustomerRepository customerRepository;

  @Override
  public List<User> getAll() {
    log.info("(Get all user : start)");
    return repository.findAll();
  }

  @Override
  public User get(Integer id) {
    log.info("(get) id:{}", id);

    try {
      return repository.findById(id).orElseThrow();
    } catch (NoSuchElementException e) {
      throw new UserNotFoundException(USER_NOT_FOUND + id);
    }
  }

  @Override
  public User create(User user) {
    log.info("(create) user:{}", user);
    boolean isUpdatingUser = (user.getId() != null);

    if (isUpdatingUser) {
      User existingUser = repository.findById(user.getId()).orElseThrow();
      log.info("(password) : {}", existingUser.getPassword());

      if (user.getPassword().isEmpty()) {
        log.info("(password) : {}", existingUser.getPassword());
        user.setPassword(existingUser.getPassword());
      } else {
        encodedPassword(user);
      }
      user.setRole(existingUser.getRole());
    } else {
      log.info("Create new user");
      encodedPassword(user);
    }

    log.info("(user) user:{}", user);

    return repository.save(user);
  }

  @Override
  public boolean isEmailUnique(Integer id, String email) {

    log.info("(isEmailUnique) id:{}, email:{}", id, email);

    if (repository.existsByEmail(email)
          && Objects.nonNull(id)
          && repository.findById(id).orElseThrow().getEmail().equals(email)) return true;

    return !repository.existsByEmail(email);
  }

  @Override
  public List<User> list(int page, int size, String sortField, String sortDir, String keyword) {
    log.info("(list) page:{}, size:{}, sortField:{}, sortDir:{}, keyword:{}", page, size, sortField, sortDir, keyword);

    log.info("(list) page:{}, size:{}", page, size);

    Sort sort = Sort.by(sortField);

    sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

    Pageable pageable = PageRequest.of(page -1 , size, sort);

    return (Objects.nonNull(keyword)) ? repository.listByKeyword(keyword, pageable) : repository.list(pageable);
  }

  @Override
  public int count() {
    log.info("(count)");
    return repository.countUser();
  }

  @Override
  public void delete(Integer id) {
    log.info("(delete) id:{}", id);
    repository.softDelete(id);
  }

  @Override
  public int countByKeyword(String keyword) {
    log.info("(countByKeyword) keyword:{}", keyword);
    return repository.countByKeyword(keyword);
  }

  private void encodedPassword(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
  }
}
