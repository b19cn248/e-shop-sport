package com.shopsport.server.exception.brand;

public class BrandNotFoundException extends RuntimeException{

  public BrandNotFoundException(String message) {
    super(message);
  }
}
