package com.ctambaoan.aggregator.model;

import lombok.Data;

@Data
public class Article {
  private Source source;
  private String category;
  private String author;
  private String title;
  private String description;
  private String url;
  private String publishedAt;
}
