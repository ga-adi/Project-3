package com.imaginat.justhejist.jist.api.nyt;

/**
 * Copyright 2016 Boloutare Doubeni
 *
 * A class that holds all of the sections used by the api calls
 */
public class Section {

  public static final String HOME = "home";
  public static final String WORLD = "world";
  public static final String NATIONAL = "national";
  public static final String POLITICS = "politics";
  public static final String NYREGION = "nyregion";
  public static final String BUSINESS = "business";
  public static final String OPINION = "opinion";
  public static final String TECHNOLOGY = "technology";
  public static final String SCIENCE = "science";
  public static final String HEALTH = "health";
  public static final String SPORTS = "sports";
  public static final String ARTS = "arts";
  public static final String FASHION = "fashion";
  public static final String DINING = "dining";
  public static final String TRAVEL = "travel";
  public static final String MAGAZINE = "magazine";
  public static final String REALESTATE = "realestate";

  private static String[] SECTIONS = {
      HOME,    WORLD,      NATIONAL, POLITICS, NYREGION,   BUSINESS,
      OPINION, TECHNOLOGY, SCIENCE,  HEALTH,   SPORTS,     ARTS,
      FASHION, DINING,     TRAVEL,   MAGAZINE, REALESTATE,
  };

  /**
   *
   * @return The sections provided by the NYT api
   */
  public static String[] getSections() { return SECTIONS; }

  private Section() {
    throw new AssertionError("This is should not be instantiated");
  }
}
