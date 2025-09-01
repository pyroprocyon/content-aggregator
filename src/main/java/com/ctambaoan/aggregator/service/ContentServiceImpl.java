package com.ctambaoan.aggregator.service;

import com.ctambaoan.aggregator.connector.NewsApiClient;
import com.ctambaoan.aggregator.connector.NewsSourceEnum;
import com.ctambaoan.aggregator.dto.ArticleDto;
import com.ctambaoan.aggregator.entity.Article;
import com.ctambaoan.aggregator.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import static java.time.LocalDateTime.now;

@Slf4j
@Service
public class ContentServiceImpl implements ContentService {

  private final NewsApiClient newsApiClient;
  private final Executor executor;
  private final ArticleRepository repository;

  public ContentServiceImpl(
      NewsApiClient newsApiClient,
      @Qualifier("contentFetcherExecutor") Executor executor,
      ArticleRepository repository) {
    this.newsApiClient = newsApiClient;
    this.executor = executor;
    this.repository = repository;
  }

  @Override
  public Page<Article> getArticles(PageRequest pageable) {
    return repository.findAll(pageable);
  }

  @Profile("!test")
  @Scheduled(fixedDelayString = "${news.api.caching.delay}")
  public void cacheData() {
    log.info("Data caching started at {}", now());
    try {
      var articles = fetchArticles().get();
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

  private CompletableFuture<List<ArticleDto>> fetchArticles() {
    var futureArticles = fetchFutureArticles();
    return CompletableFuture.allOf(futureArticles.toArray(new CompletableFuture[0]))
        .thenApply(v -> futureArticles.stream()
            .map(CompletableFuture::join)
            .flatMap(List::stream)
            .toList());
  }

  private List<CompletableFuture<List<ArticleDto>>> fetchFutureArticles() {
    return Arrays.stream(NewsSourceEnum.values())
        .map(value -> CompletableFuture.supplyAsync(
            () -> newsApiClient.fetchArticles(value.name()), executor))
        .toList();
  }

}
