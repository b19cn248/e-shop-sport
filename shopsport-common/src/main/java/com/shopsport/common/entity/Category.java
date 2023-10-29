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

    return "/shopsport-parent/category-images/" + this.id + "/" + this.image;
  }
}
