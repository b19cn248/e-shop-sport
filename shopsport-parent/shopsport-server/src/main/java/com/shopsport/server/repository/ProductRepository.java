package com.shopsport.server.repository;

import com.shopsport.common.entity.Product;
import com.shopsport.server.dto.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

  Product findByName(String name);

  @Query("SELECT new com.shopsport.server.dto.ProductResponse(b.name, SUM(od.quantity)) " +
        "FROM OrderDetail od " +
        "JOIN od.product p " +
        "JOIN p.brand b " +
        "GROUP BY b.name")
  List<ProductResponse> statisticsByBrand();


  @Query(" SELECT sum(od.quantity * (od.exportPrice * (1 - p.disCount / 100) - od.importPrice)) " +
        " FROM OrderDetail od join Product p on od.product.id = p.id" +
        " WHERE od.lastUpdatedAt BETWEEN :from AND :to")
  Double totalMoney(Long from, Long to);
}
