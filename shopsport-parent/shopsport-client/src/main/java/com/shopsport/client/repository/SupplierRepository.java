package com.shopsport.client.repository;

import com.shopsport.common.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
