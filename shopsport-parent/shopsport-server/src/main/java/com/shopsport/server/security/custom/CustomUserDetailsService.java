package com.shopsport.server.security.custom;

import com.shopsport.common.entity.User;
import com.shopsport.server.exception.UserNotFoundException;
import com.shopsport.server.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> user = repository.findByEmail(email);
    if (user.isEmpty()) {
      throw new UserNotFoundException("Could not find user with email: " + email);
    }

    return new CustomUserDetails(user.get());
  }
}
