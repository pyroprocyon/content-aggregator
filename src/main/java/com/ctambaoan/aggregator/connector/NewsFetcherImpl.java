package com.ctambaoan.aggregator.connector;

import com.ctambaoan.aggregator.dto.ArticleDto;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class NewsFetcherImpl implements NewsFetcher {

  private final NewsApiClient newsApiClient;
  private final Executor executor;

  public NewsFetcherImpl(
      NewsApiClient newsApiClient,
      @Qualifier("contentFetcherExecutor") Executor executor) {
    this.newsApiClient = newsApiClient;
    this.executor = executor;
  }

  @Override
  public CompletableFuture<List<ArticleDto>> fetchArticles() {
    var futureArticles = fetchFutureArticles();
    return CompletableFuture.allOf(futureArticles.toArray(new CompletableFuture[0]))
        .thenApply(v -> futureArticles.stream()
            .map(CompletableFuture::join)
            .flatMap(List::stream)
            .toList());
  }

  private List<CompletableFuture<List<ArticleDto>>> fetchFutureArticles() {
    return Arrays.stream(NewsCategory.values())
        .map(category -> CompletableFuture.supplyAsync(
            () -> newsApiClient.fetchArticles(category), executor))
        .toList();
  }
}
