package com.shopsport.client.config.auditor;


import com.shopsport.client.security.custom.CustomUserDetails;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {
  @Override
  public @NonNull Optional<String> getCurrentAuditor() {

    log.info("Da toi ham audit");

    Object authenticationToken = SecurityContextHolder.getContext().getAuthentication();

    if (authenticationToken instanceof UsernamePasswordAuthenticationToken) {

      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authenticationToken;

      CustomUserDetails myUserDetails = (CustomUserDetails) usernamePasswordAuthenticationToken.getPrincipal();

      return Optional.ofNullable(myUserDetails.getUser().getFullName());
    } else {
      RememberMeAuthenticationToken rememberMeAuthenticationToken = (RememberMeAuthenticationToken) authenticationToken;

      CustomUserDetails myUserDetails = (CustomUserDetails) rememberMeAuthenticationToken.getPrincipal();

      return Optional.ofNullable(myUserDetails.getUser().getFullName());
    }

  }

  private boolean isAnonymous() {
    return SecurityContextHolder.getContext().getAuthentication().getName().equals("ANONYMOUS");
  }
}

