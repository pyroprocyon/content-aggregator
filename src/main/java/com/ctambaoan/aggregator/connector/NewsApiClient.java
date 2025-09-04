package com.ctambaoan.aggregator.connector;

import com.ctambaoan.aggregator.dto.ArticleDto;
import com.ctambaoan.aggregator.dto.NewsArticleResponse;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class NewsApiClient {

  private final String baseUrl;
  private final RestTemplate restTemplate;

  public NewsApiClient(RestTemplate restTemplate, @Value("${news.api.url}") String baseUrl) {
    this.restTemplate = restTemplate;
    this.baseUrl = baseUrl;
  }

  public List<ArticleDto> fetchArticles(NewsCategory category) {
    var articleResponse = getNewsArticleResponse(category.name());
    if (articleResponse == null) {
      return Collections.emptyList();
    }
    List<ArticleDto> articles = articleResponse.getArticles();
    articles.forEach(article -> article.setCategory(category.name().toLowerCase()));
    log.info("{} article response size: {}", category, articles.size());
    return articles;
  }

  private NewsArticleResponse getNewsArticleResponse(String category) {
    String newUrl = String.format("%s&category=%s", baseUrl, category);
    log.info("Fetching content from: {}", newUrl);
    return restTemplate.getForObject(newUrl, NewsArticleResponse.class);
  }

}
