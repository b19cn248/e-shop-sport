package com.shopsport.server.repository;

import com.shopsport.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shopsport.common.entity.ShopsportCommonApplication;

public interface UserRepository extends JpaRepository<User, Integer> {
  boolean existsByEmail(String email);
}
