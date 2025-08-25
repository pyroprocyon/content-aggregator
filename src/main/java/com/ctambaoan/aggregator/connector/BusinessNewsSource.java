package com.ctambaoan.aggregator.connector;

import com.ctambaoan.aggregator.dto.ArticleDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.ctambaoan.aggregator.connector.NewsSourceEnum.BUSINESS;

@Component("business")
public class BusinessNewsSource extends ContentSource {

  private final NewsApiClient apiClient;

  public BusinessNewsSource(NewsApiClient apiClient) {
    super(BUSINESS.name());
    this.apiClient = apiClient;
  }

  @Override
  public List<ArticleDto> fetchArticles() {
    return apiClient.fetchArticles(BUSINESS.name());
  }
}
