package com.shopsport.server.security;


import com.shopsport.server.repository.UserRepository;
import com.shopsport.server.security.custom.CustomLoginSuccessHandler;
import com.shopsport.server.security.custom.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public UserDetailsService userDetailsService(UserRepository repository) {
    return new CustomUserDetailsService(repository);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider(UserRepository repository) {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService(repository));
    authenticationProvider.setPasswordEncoder(passwordEncoder());

    return authenticationProvider;
  }

  @Bean
  public AuthenticationManager authManager(HttpSecurity http, UserRepository repository) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder =
          http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider(repository));
    return authenticationManagerBuilder.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
  }

  @Bean
  public CustomLoginSuccessHandler customLoginSuccessHandler() {
    return new CustomLoginSuccessHandler();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
    http.authorizeHttpRequests(requests -> requests
                .anyRequest().authenticated()
          )
          .formLogin(
                form -> form
                      .loginPage("/login")
                      .usernameParameter("email")
                      .permitAll()
                      .successHandler(customLoginSuccessHandler())
          )
          .logout(LogoutConfigurer::permitAll)
          .rememberMe(remember -> remember
                .rememberMeServices(rememberMeServices)
          );

    return http.build();

  }

  @Bean
  public RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
    TokenBasedRememberMeServices.RememberMeTokenAlgorithm encodingAlgorithm = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
    TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices("AbcDEF@_123456789", userDetailsService, encodingAlgorithm);
    rememberMe.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
    rememberMe.setTokenValiditySeconds(60 * 60 * 7 * 24);
    rememberMe.setParameter("remember-me-custom");
    return rememberMe;
  }


}
