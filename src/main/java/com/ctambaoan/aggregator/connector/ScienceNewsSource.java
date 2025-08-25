package com.ctambaoan.aggregator.connector;

import com.ctambaoan.aggregator.dto.ArticleDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.ctambaoan.aggregator.connector.NewsSourceEnum.SCIENCE;

@Component("science")
public class ScienceNewsSource extends ContentSource {

  private final NewsApiClient apiClient;

  public ScienceNewsSource(NewsApiClient apiClient) {
    super(SCIENCE.name());
    this.apiClient = apiClient;
  }

  @Override
  public List<ArticleDto> fetchArticles() {
    return apiClient.fetchArticles(SCIENCE.name());
  }
}
