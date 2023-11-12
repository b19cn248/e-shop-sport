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
    registerResourceHandler(registry, "user-photos");
    registerResourceHandler(registry, "category-images");
    registerResourceHandler(registry, "brand-logos");
    registerResourceHandler(registry, "product-images");
  }

  private void registerResourceHandler(ResourceHandlerRegistry registry, String folderName) {
    Path resourceDir = Paths.get(folderName);
    String resourcePath = resourceDir.toFile().getAbsolutePath();
    Objects.requireNonNull(registry).addResourceHandler("/" + folderName + "/**").addResourceLocations("file:" + resourcePath + "/");
  }

}
