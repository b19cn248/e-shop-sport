package com.shopsport.server.repository;

import com.shopsport.common.entity.Role;
import com.shopsport.common.entity.User;
import com.shopsport.server.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

  @Autowired
  private UserRepository repo;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void testCreateNewUserWithOneRole() {

    Role role = entityManager.find(Role.class, 1);

    User userNamHM = new User("hieunm123.ptit@gmail.com", "Hieu230708@", "Hieu", "Nguyen Minh");

    userNamHM.setRole(role);

    User savedUser = repo.save(userNamHM);

    assertThat(savedUser.getId()).isGreaterThan(0);
  }
}