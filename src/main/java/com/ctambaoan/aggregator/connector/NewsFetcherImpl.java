package com.ctambaoan.aggregator.connector;

import com.ctambaoan.aggregator.dto.ArticleDto;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsFetcherImpl implements NewsFetcher {

  private final NewsApiClient newsApiClient;
  private final ExecutorService executor;

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
