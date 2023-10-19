package com.shopsport.server.exception;

public class UsernameNotFoundException extends RuntimeException{

  public UsernameNotFoundException() {
    super("User name not found");
  }
}
