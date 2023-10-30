package com.shopsport.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "categories")
@Builder
public class Category extends BaseEntityWithUpdater {

  private String name;
  private String description;
  private String image;
  private String alias;
  private boolean enabled;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private Category parent;

  @OneToMany(mappedBy = "parent")
  private Set<Category> children = new HashSet<>();

  @Column(name = "is_deleted")
  private boolean isDeleted;

  public Category(Integer id) {
    super.setId(id);
  }

  public Category(Integer id, String name) {
    super.setId(id);
    this.name = name;
  }

  @Transient
  public String getImagePath() {
    if (Objects.isNull(this.getImage())) return "/images/image-thumbnail.png";

    return "/category-images/" + this.id + "/" + this.image;
  }

  public static Category copyIdAndName(Integer id, String name) {
    Category copyCategory = new Category();
    copyCategory.setId(id);
    copyCategory.setName(name);

    return copyCategory;
  }

  public static Category copyFull(Category category) {
    Category copyCategory = new Category();
    copyCategory.setId(category.getId());
    copyCategory.setName(category.getName());
    copyCategory.setImage(category.getImage());
    copyCategory.setAlias(category.getAlias());
    copyCategory.setEnabled(category.isEnabled());

    return copyCategory;
  }

  public static Category copyFull(Category category, String name) {
    Category copyCategory = Category.copyFull(category);
    copyCategory.setName(name);

    return copyCategory;
  }

  @Override
  public String toString() {
    return "Category{" +
          "name='" + name + '\'' +
          ", imagePath='" + getImagePath() + '\'' +
          ", image='" + image + '\'' +
          '}';
  }
}
