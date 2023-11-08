package com.shopsport.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
public class Customer extends User {
}
