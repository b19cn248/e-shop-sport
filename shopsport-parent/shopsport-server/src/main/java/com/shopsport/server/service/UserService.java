package com.shopsport.server.service;

import com.shopsport.common.entity.User;

import java.util.List;

public interface UserService {

  List<User> getAll();

  User get(Integer id);

  User create(User user);

  boolean isEmailUnique(Integer id, String email);
}
