package com.imaginat.justhejist.jist.api.nyt;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public class WebScraper {

  public static String parseHTML(String url) throws IOException {
   return Jsoup.parse(new URL(url), 5000).body().getElementsByClass("article-body").text();
  }
}
