package com.shopsport.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product extends BaseEntityWithUpdater {

  private String code;
  private String name;
  private String alias;
  private String unit;
  private String origin;
  @Column(length = 256)
  private String shortDescription;

  @Column(length = 2048)
  private String fullDescription;
  private Double importPrice;
  private Double exportPrice;
  private Integer quantity;
  private String image;
  private Boolean enabled;
  private Boolean inStock;
  private Double disCount;

  @Column(nullable = false)
  private String mainImage;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "warehouse_id")
  private Warehouse warehouse;

  @ManyToOne
  @JoinColumn(name = "brand_id")
  private Brand brand;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<ProductImage> images = new HashSet<>();

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProductDetail> details = new ArrayList<>();


  public Product(Integer id, String code, String name, String unit, String origin, String description, Double importPrice, Double exportPrice, Integer quantity) {
    super.setId(id);
    this.code = code;
    this.name = name;
    this.unit = unit;
    this.origin = origin;
    this.shortDescription = description;
    this.importPrice = importPrice;
    this.exportPrice = exportPrice;
    this.quantity = quantity;
  }

  @Transient
  public String getImagePath() {
    return "product-images/" + this.image;
  }

  @Transient
  public String getDesc() {
    return this.getName() + " o " + this.warehouse.getDescription();
  }

  public void addExtraImage(String imageName) {
    this.images.add(new ProductImage(imageName, this));
  }

  public void addDetail(String name, String value) {
    this.details.add(new ProductDetail(name, value, this));
  }

  public void addDetail(Integer id, String name, String value) {
    this.details.add(new ProductDetail(id, name, value, this));
  }

  @Transient
  public String getMainImagePath() {
    if (id == null || mainImage == null) return "/images/image-thumbnail.png";
    return "/product-images/" + this.id + "/" + this.mainImage;
  }

  public boolean containsImageName(String imageName) {
    Iterator<ProductImage> iterator = images.iterator();

    while (iterator.hasNext()) {
      ProductImage image = iterator.next();
      if (image.getName().equals(imageName)) {
        return true;
      }
    }
    return false;
  }

  @Transient
  public String getUpdatedTime() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    return dateFormat.format(super.getLastUpdatedAt());
  }
}
