package com.ctambaoan.aggregator.connector;

import com.ctambaoan.aggregator.model.Article;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("science")
public class ScienceNewsSource extends ContentSource {

  public static final String SCIENCE = "science";
  private final NewsApiClient apiClient;

  public ScienceNewsSource(NewsApiClient apiClient) {
    super(SCIENCE);
    this.apiClient = apiClient;
  }

  @Override
  public List<Article> fetchArticles() {
    return apiClient.fetchArticles(SCIENCE);
  }
}
