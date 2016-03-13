package com.imaginat.justhejist.jist.utils;

import java.util.List;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public class Utils {

  public static String joinString(List<String> words) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < words.size(); ++i) {
      builder.append(words.get(i));
      if (i < words.size() - 1) {
        builder.append(",");
      }
    }
    return builder.toString();
  }
  private Utils() {}
}
