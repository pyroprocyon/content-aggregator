package com.ctambaoan.aggregator.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewsArticleResponse {
  private List<ArticleDto> articles = new ArrayList<>();
}
