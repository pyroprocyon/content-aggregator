package com.ctambaoan.aggregator.model;

import lombok.Data;

import java.util.List;

@Data
public class NewsArticleResponse {
  private List<Article> articles;
}
