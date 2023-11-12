package com.shopsport.server.exception.user;

public class UsernameNotFoundException extends RuntimeException{

  public UsernameNotFoundException() {
    super("User name not found");
  }
}
