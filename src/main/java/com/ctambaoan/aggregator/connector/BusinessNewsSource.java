package com.ctambaoan.aggregator.connector;

import com.ctambaoan.aggregator.model.Article;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("business")
public class BusinessNewsSource extends ContentSource {

  public static final String BUSINESS = "business";
  private final NewsApiClient apiClient;

  public BusinessNewsSource(NewsApiClient apiClient) {
    super(BUSINESS);
    this.apiClient = apiClient;
  }

  @Override
  public List<Article> fetchArticles() {
    return apiClient.fetchArticles(BUSINESS);
  }
}
