package com.shopsport.server.exception;

public class BrandNotFoundException extends RuntimeException{

  public BrandNotFoundException(String message) {
    super(message);
  }
}
