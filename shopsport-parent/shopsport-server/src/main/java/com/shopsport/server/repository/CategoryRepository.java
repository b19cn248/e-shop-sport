package com.shopsport.server.repository;

import com.shopsport.common.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

  @Query("select c from Category c where c.parent = null and c.isDeleted = false ")
  List<Category> getRootCategories();

  @Query("select c from Category c where c.isDeleted = false ")
  List<Category> list();

  @Query("update Category c set c.isDeleted = true where c.id = :id")
  @Modifying
  @Transactional
  void delete(Integer id);
}
