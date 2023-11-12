package com.shopsport.server.repository;

import com.shopsport.common.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
  Long countById(Integer id);

  Brand findByName(String name);

  @Query("SELECT b FROM Brand b WHERE b.name LIKE %?1%")
  Page<Brand> findAll(String keyword, Pageable pageable);

  @Query("select new Brand (b.id, b.name) FROM Brand b order by b.name desc ")
  List<Brand> listAll();
}
