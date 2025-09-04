package com.ctambaoan.aggregator.service;

import static java.time.LocalDateTime.now;

import com.ctambaoan.aggregator.connector.NewsFetcher;
import com.ctambaoan.aggregator.dto.ArticleDto;
import com.ctambaoan.aggregator.entity.Article;
import com.ctambaoan.aggregator.repository.ArticleRepository;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

  private final NewsFetcher newsFetcher;
  private final ArticleRepository repository;

  @Override
  public Page<Article> getArticles(PageRequest pageable) {
    return repository.findAll(pageable);
  }

  @Override
  public Page<Article> getArticlesByCategory(String category, PageRequest pageable) {
    return repository.findByCategoryIgnoreCase(category, pageable);
  }

  @Profile("!test")
  @Scheduled(fixedDelayString = "${news.api.caching.delay}")
  public void cacheData() {
    log.info("Data caching started at {}", now());
    try {
      var articles = newsFetcher.fetchArticles().get();
      articles.stream()
          .filter(this::isNewArticle)
          .map(Article::new)
          .forEach(repository::save);
    } catch (InterruptedException | ExecutionException e) {
      log.error("Error caching articles", e);
      Thread.currentThread().interrupt();
    } finally {
      log.info("Data caching finished at {}", now());
    }
  }

  private boolean isNewArticle(ArticleDto dto) {
    return !repository.existsByUrl(dto.getUrl());
  }

}
