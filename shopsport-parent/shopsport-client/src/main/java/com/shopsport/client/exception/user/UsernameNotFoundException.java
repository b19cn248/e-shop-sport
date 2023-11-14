package com.shopsport.client.exception.user;

public class UsernameNotFoundException extends RuntimeException{

  public UsernameNotFoundException() {
    super("User name not found");
  }
}
