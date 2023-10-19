package com.shopsport.server.role;

import com.shopsport.common.entity.Role;
import com.shopsport.server.repository.RoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RepositoryTests {

  @Autowired
  private RoleRepository roleRepository;

  @Test
  public void testCreateFirstRole() {
    Role roleAdmin = Role.builder()
          .name("Admin")
          .description("manage everything")
          .build();

    Role savedRole = roleRepository.save(roleAdmin);

    Assertions.assertThat(savedRole.getId()).isGreaterThan(0);
  }

  @Test
  public void testCreateRestRoles() {
    Role roleSalesperson = new Role("Salesperson", "manage product price, "
          + "customers, shipping, orders and sales report");

    Role roleEditor = new Role("Editor", "manage categories, brands, "
          + "products, articles and menus");

    Role roleShipper = new Role("Shipper", "view products, view orders "
          + "and update order status");

    Role roleAssistant = new Role("Assistant", "manage questions and reviews");

    roleRepository.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
  }

}
