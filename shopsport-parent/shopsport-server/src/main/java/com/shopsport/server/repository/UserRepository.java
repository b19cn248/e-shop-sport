package com.shopsport.server.repository;

import com.shopsport.common.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  boolean existsByEmail(String email);

  Optional<User> findByEmail(String email);

  @Query("SELECT u FROM User u WHERE CONCAT(u.id,' ',u.email,' ',u.firstName,' ',u.lastName) like %?1%")
  List<User> listByKeyword(String keyword, Pageable pageable);

  @Query(value = "select u from User u where u.isDeleted = false ")
  List<User> list(Pageable pageable);

  @Query(value = "select count(u) from User u where u.isDeleted = false ")
  int countUser();

  @Query("SELECT count(u) FROM User u WHERE CONCAT(u.id,' ',u.email,' ',u.firstName,' ',u.lastName) like %?1%")
  int countByKeyword(String keyword);

  @Query("update User u set u.isDeleted = true  where u.id = :id")
  @Modifying
  @Transactional
  void softDelete(Integer id);
}
