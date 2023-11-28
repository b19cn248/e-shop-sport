package com.shopsport.client.repository;

import com.shopsport.client.dto.ProductResponse;
import com.shopsport.common.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

  @Query("select new com.shopsport.client.dto.ProductResponse(p.id, p.name, p.importPrice, p.exportPrice, p.disCount, p.quantity, p.mainImage) " +
        " from Product p where p.brand.id in :brandIDs")
  List<ProductResponse> findProductByBrand(List<Integer> brandIDs);
}
