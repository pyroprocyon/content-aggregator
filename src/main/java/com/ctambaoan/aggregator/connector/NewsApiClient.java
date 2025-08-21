package com.ctambaoan.aggregator.connector;

import com.ctambaoan.aggregator.model.Article;
import com.ctambaoan.aggregator.model.NewsArticleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class NewsApiClient {
  private final String baseUrl;
  private final RestTemplate restTemplate;

  public NewsApiClient(RestTemplate restTemplate, @Value("${news.api.url}") String baseUrl) {
    this.restTemplate = restTemplate;
    this.baseUrl = baseUrl;
  }

  public List<Article> fetchArticles(String category) {
    String newUrl = String.format("%s&category=%s", baseUrl, category);
    NewsArticleResponse articleResponse = restTemplate.getForObject(newUrl, NewsArticleResponse.class);
    if (articleResponse == null || articleResponse.getArticles() == null) {
      return Collections.emptyList();
    }
    articleResponse.getArticles().forEach(article -> article.setCategory(category));
    return articleResponse.getArticles();
  }
}
