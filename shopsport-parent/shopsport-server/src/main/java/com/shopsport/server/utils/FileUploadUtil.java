package com.shopsport.server.utils;

import com.shopsport.common.entity.Product;
import com.shopsport.common.entity.ProductImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static com.shopsport.server.constant.CommonConstants.ProductConstants.PRODUCT_IMAGES;

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
        String uploadDir = PRODUCT_IMAGES + savedProduct.getId();

        FileUploadUtil.cleanDir(uploadDir);
        FileUploadUtil.saveFile(uploadDir, fileName, mainImageMultipart);
      }
    }

    if (extraImageMultipart.length > 0) {
      String uploadDir = PRODUCT_IMAGES + savedProduct.getId() + "/extras";

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

  public static void setExistingExtraImageNames(String[] imageIDs, String[] imageNames, Product product) {

    if (imageIDs == null || imageIDs.length == 0) return;

    Set<ProductImage> images = new HashSet<>();

    for (int i = 0; i < imageIDs.length; i++) {
      Integer id = Integer.parseInt(imageIDs[i]);
      String name = imageNames[i];
      images.add(new ProductImage(id, name, product));
    }

    product.setImages(images);

  }

  public static void deleteExtraImagesWereRemovedOnForm(Product product) {

    String extraImageDir = PRODUCT_IMAGES + product.getId() + "/extras";

    Path dirPath = Paths.get(extraImageDir);

    try (Stream<Path> pathStream = Files.list(dirPath)) {
      pathStream.forEach(file -> {
        String fileName = file.toFile().getName();
        if (!product.containsImageName(fileName)) {
          try {
            Files.delete(file);
            log.info("Deleted extra image: " + fileName);
          } catch (IOException e) {
            log.error("Could not delete extra image: " + fileName);
          }
        }
      });
    } catch (IOException e) {
      log.error("Could not list directory: " + dirPath);
    }
  }


}
