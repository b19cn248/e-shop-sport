package com.shopsport.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.shopsport.common.entity"})
public class ShopsportClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShopsportClientApplication.class, args);
  }

}
