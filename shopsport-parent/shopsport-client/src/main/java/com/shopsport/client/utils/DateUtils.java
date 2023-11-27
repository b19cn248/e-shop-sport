package com.shopsport.client.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface DateUtils {

  static String getDateNow() {
    LocalDate currentDate = LocalDate.now();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    return currentDate.format(formatter);
  }

}
