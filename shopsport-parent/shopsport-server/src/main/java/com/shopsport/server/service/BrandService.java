package com.shopsport.server.service;

import com.shopsport.common.entity.Brand;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BrandService {

  List<Brand> list();

  List<Brand> listAll();

  Page<Brand> listByPage(int page, String sortField, String sortDir, String keyword);

  Brand save(Brand brand);

  Brand get(Integer id);

  void delete(Integer id);

  String checkUnique(Integer id, String name);
}
