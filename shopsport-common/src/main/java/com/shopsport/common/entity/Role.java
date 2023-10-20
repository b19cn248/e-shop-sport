package com.shopsport.common.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "roles")
@Builder
@Data
@NoArgsConstructor
public class Role extends BaseEntityWithUpdater implements Serializable {

  @Column(length = 40, nullable = false, unique = true)
  private String name;

  @Column(length = 1500, nullable = false)
  private String description;

  public Role(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public Role(Integer id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Role role = (Role) o;
    return Objects.equals(id, role.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return this.name;
  }
}
