package com.shopsport.server.utils;

import com.shopsport.common.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Slf4j
public class FileUploadUtil {

  private FileUploadUtil() {

  }

  public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
    Path uploadPath = Paths.get(uploadDir);


    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    try (InputStream inputStream = multipartFile.getInputStream()) {
      Path filePath = uploadPath.resolve(fileName);
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException ex) {
      throw new IOException("Could not save file: " + fileName, ex);
    }
  }

  public static void cleanDir(String dir) {
    Path dirPath = Paths.get(dir);

    if (Files.exists(dirPath)) {
      try (Stream<Path> paths = Files.list(dirPath)) {
        paths.forEach(file -> {
          if (!Files.isDirectory(file)) {
            try {
              Files.delete(file);
            } catch (IOException e) {
              log.error("Could not delete file: " + file);
            }
          }
        });
      } catch (IOException e) {
        log.error(("Could not list directory: " + dirPath));
      }
    }
  }

  public static void removeDir(String dir) {
    cleanDir(dir);

    try {
      Files.delete(Paths.get(dir));
    } catch (IOException e) {
      log.error("Could not remove directory: " + dir);
    }

  }

  public static void saveUploadedImages(
        MultipartFile mainImageMultipart,
        MultipartFile[] extraImageMultipart, Product savedProduct
  ) throws IOException {

    log.debug("(saveUploadedImages) mainImageMultipart:{}, extraImageMultipart:{} ",
          mainImageMultipart, extraImageMultipart);

    if (!mainImageMultipart.isEmpty()) {
      String originalFilename = mainImageMultipart.getOriginalFilename();
      if (originalFilename != null) {
        String fileName = StringUtils.cleanPath(originalFilename);
        String uploadDir = "product-images/" + savedProduct.getId();

        FileUploadUtil.cleanDir(uploadDir);
        FileUploadUtil.saveFile(uploadDir, fileName, mainImageMultipart);
      }
    }

    if (extraImageMultipart.length > 0) {
      String uploadDir = "product-images/" + savedProduct.getId() + "/extras";

      for (MultipartFile multipartFile : extraImageMultipart) {
        if (multipartFile.isEmpty()) continue;

        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename != null) {
          String fileName = StringUtils.cleanPath(originalFilename);
          FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
      }
    }
  }


  public static void setNewExtraImageNames(MultipartFile[] extraImageMultipart, Product product) {


    for (MultipartFile multipartFile : extraImageMultipart) {

      if (!multipartFile.isEmpty()) {
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename != null) {
          String fileName = StringUtils.cleanPath(originalFilename);

          if (!product.containsImageName(fileName)) {
            product.addExtraImage(fileName);
          }

        }
      }
    }

    System.out.println(product.getImages());
  }


  public static void setMainImageName(MultipartFile mainImageMultipart, Product product) {
    if (!mainImageMultipart.isEmpty()) {
      String originalFilename = mainImageMultipart.getOriginalFilename();
      if (originalFilename != null) {
        String fileName = StringUtils.cleanPath(originalFilename);
        product.setMainImage(fileName);
      }
    }
  }

}
