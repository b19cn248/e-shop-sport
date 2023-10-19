package com.shopsport.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.shopsport.common.entity"})
public class ShopsportServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShopsportServerApplication.class, args);
  }

}
