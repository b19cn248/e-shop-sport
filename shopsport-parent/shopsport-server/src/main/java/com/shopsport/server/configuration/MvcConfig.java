package com.shopsport.server.configuration;

import jakarta.annotation.Nullable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(@Nullable ResourceHandlerRegistry registry) {

    String dirName = "user-photos";
    Path userPhotosDir = Paths.get("user-photos");
    String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
    Objects.requireNonNull(registry).addResourceHandler("/"+ dirName +"/**").addResourceLocations("file:" + userPhotosPath + "/");

  }
}
