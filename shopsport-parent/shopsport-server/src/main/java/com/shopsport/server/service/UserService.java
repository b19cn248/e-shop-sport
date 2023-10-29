package com.shopsport.server.service;

import com.shopsport.common.entity.User;

import java.util.List;

public interface UserService {

  List<User> getAll();

  User get(Integer id);

  User create(User user);

  boolean isEmailUnique(Integer id, String email);

  List<User> list(int page, int size, String sortField, String sortDir, String keyword);

  int count();

  void delete(Integer id);

  int countByKeyword(String keyword);
}
