package com.ctambaoan.aggregator.connector;

import com.ctambaoan.aggregator.model.Article;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("technology")
public class TechnologyNewsSource extends ContentSource {

  public static final String TECHNOLOGY = "technology";
  private final NewsApiClient apiClient;

  public TechnologyNewsSource(NewsApiClient apiClient) {
    super(TECHNOLOGY);
    this.apiClient = apiClient;
  }

  @Override
  public List<Article> fetchArticles() {
    return apiClient.fetchArticles(TECHNOLOGY);
  }
}
