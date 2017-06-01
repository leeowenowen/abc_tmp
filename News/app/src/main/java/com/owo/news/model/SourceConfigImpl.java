package com.owo.news.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangli on 17-5-3.
 */

public class SourceConfigImpl implements SourceConfig{
  private List<String> categories;
  private List<String> languages;
  private List<String> countries;

  public SourceConfigImpl() {
    categories = new ArrayList<>();
    categories.add("business");
    categories.add("entertainment");
    categories.add("gaming");
    categories.add("general");
    categories.add("music");
    categories.add("science-and-nature");
    categories.add("sport");
    categories.add("technology");

    /**
     * (optional) - The 2-letter ISO-639-1 code of the language you would like to get sources for.
     Possible options: en, de, fr.
     Default: empty (all sources returned)
     */
    languages = new ArrayList<>();
    /**
     * (optional) - The 2-letter ISO 3166-1 code of the country you would like to get sources for.
     Possible options: au, de, gb, in, it, us.
     Default: empty (all sources returned)
     */
    countries = new ArrayList<>();
  }

  public List<String> categories() {
    return categories;
  }

  @Override
  public String country() {
    return "";
  }

  @Override
  public String language() {
    return "";
  }

  public void setCategories(List<String> categories) {
    this.categories = categories;
  }

  public List<String> languages() {
    return languages;
  }

  public void setLanguages(List<String> languages) {
    this.languages = languages;
  }

  public List<String> countries() {
    return countries;
  }

  public void setCountries(List<String> countries) {
    this.countries = countries;
  }
}
