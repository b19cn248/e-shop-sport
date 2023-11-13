package com.shopsport.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntityWithUpdater implements Serializable {

  @Column(length = 128, nullable = false, unique = true)
  private String email;

  @Column(length = 64, nullable = false)
  private String password;

  @Column(length = 45, nullable = false)
  private String firstName;

  @Column(length = 45, nullable = false)
  private String lastName;

  @Column(length = 64)
  private String photos;

  private boolean enabled;

  @Column(name = "is_deleted")
  private boolean isDeleted;

  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  public User(String email, String password, String firstName, String lastName) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Transient
  public String getPhotosImagePath() {
    if (Objects.isNull(photos) || Objects.isNull(id)) return "/images/default-user.png";
    return "/user-photos/" + this.id + "/" + this.photos;
  }

  @Transient
  public String getFullName() {
    return firstName + " " + lastName;
  }

}
