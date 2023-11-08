package com.shopsport.client.repository;

import com.shopsport.common.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
}
