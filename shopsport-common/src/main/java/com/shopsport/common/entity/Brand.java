package com.shopsport.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brands")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Brand extends BaseEntityWithUpdater {

  private String name;
  private String logo;

  @ManyToMany
  @JoinTable(
        name = "brand_category",
        joinColumns = @JoinColumn(name = "brand_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  private Set<Category> categories = new HashSet<>();

  public Brand(Integer id, String name) {
    super.setId(id);
    this.name = name;
  }

  @Transient
  public String getLogoPath() {
    if (this.id == null) return "/images/image-thumbnail.png";

    return "/brand-logos/" + this.id + "/" + this.logo;
  }
}
