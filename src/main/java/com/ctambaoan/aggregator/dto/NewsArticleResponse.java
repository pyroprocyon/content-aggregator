package com.ctambaoan.aggregator.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class NewsArticleResponse {

  private List<ArticleDto> articles = new ArrayList<>();
}
