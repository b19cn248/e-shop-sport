package com.shopsport.server.constant;

public class CommonConstants {

  private CommonConstants() {
  }

  public static class UserConstants {

    private UserConstants() {
    }

    public static final String MESSAGE = "message";
    public static final String CREATE_USER_SUCCESS = "The user has been saved successfully";
    public static final String USER_NOT_FOUND = "Could not find any user with ID ";

    public static final String CREATE_NEW_USER = "Create new user";

    public static final String EDIT_USER = "Edit User with ID ";

    public static final String DELETE_USER_SUCCESS = "The user has been deleted successfully";

    public static final String ENABLED_USER_SUCCESS = "The user has been enabled";

    public static final String DISABLED_USER_SUCCESS = "The user has been disabled";

    public static final int USER_PER_PAGE = 4;

    public static final String USERS_URL = "redirect:/users";

  }

  public static class CategoryConstants {
    private CategoryConstants() {

    }

    public static final String CATEGORY_FORM = "categories/category_form";

    public static final String CATEGORIES_IMAGES = "shopsport-parent/category-images/";

    public static final String SAVED_CATEGORY_SUCCESSFULLY = "The category has been saved successfully.";

    public static final String CATEGORY_URL = "redirect:/categories";

    public static final String CATEGORY_PAGE_TITLE = "Add new Category";
  }

  public static class CommonMessage {
    private CommonMessage() {
    }

    public static final String SYSTEM = "SYSTEM";
    public static final String ANONYMOUS = "ANONYMOUS";
  }

  public static class ExporterMessage {

    private ExporterMessage() {
    }

    public static final String EXCEL_CONTENT_TYPE = "application/octet-stream";
    public static final String EXCEL_EXTENSION = ".xlsx";

    public static final String CSV_CONTENT_TYPE = "text/csv";
    public static final String CSV_EXTENSION = ".csv";

    public static final String PDF_CONTENT_TYPE = "application/pdf";
    public static final String PDF_EXTENSION = ".pdf";

    public static final String TITLE_OF_PDF_FILE = "List of Users";
  }
}
