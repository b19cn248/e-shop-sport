package com.shopsport.server.repository;

import com.shopsport.common.entity.OrderDetail;
import com.shopsport.server.dto.TopProductResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
  @Query("SELECT new com.shopsport.server.dto.TopProductResponse(od.product.name, SUM(od.quantity)) " +
        "FROM OrderDetail od GROUP BY od.product.name ORDER BY SUM(od.quantity) DESC")
  List<TopProductResponse> findTopSellingProducts(Pageable pageable);

}
