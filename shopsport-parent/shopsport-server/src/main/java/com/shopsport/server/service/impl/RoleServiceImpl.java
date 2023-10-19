package com.shopsport.server.service.impl;

import com.shopsport.common.entity.Role;
import com.shopsport.server.repository.RoleRepository;
import com.shopsport.server.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

  private final RoleRepository repository;

  @Override
  public List<Role> getAll() {
    log.info("(Get all user : start)");

    return repository.findAll();
  }
}
