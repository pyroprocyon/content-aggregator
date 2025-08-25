package com.ctambaoan.aggregator.connector;

import com.ctambaoan.aggregator.dto.ArticleDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.ctambaoan.aggregator.connector.NewsSourceEnum.TECHNOLOGY;

@Component("technology")
public class TechnologyNewsSource extends ContentSource {

  private final NewsApiClient apiClient;

  public TechnologyNewsSource(NewsApiClient apiClient) {
    super(TECHNOLOGY.name());
    this.apiClient = apiClient;
  }

  @Override
  public List<ArticleDto> fetchArticles() {
    return apiClient.fetchArticles(TECHNOLOGY.name());
  }
}
