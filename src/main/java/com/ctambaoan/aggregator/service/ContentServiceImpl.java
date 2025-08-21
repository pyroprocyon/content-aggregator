package com.ctambaoan.aggregator.service;

import com.ctambaoan.aggregator.connector.ContentSource;
import com.ctambaoan.aggregator.model.Article;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class ContentServiceImpl implements ContentService {

  private final List<ContentSource> sources;
  private final ExecutorService executor;
//  private final Map<String, ContentSource> sourceMap;

  public ContentServiceImpl(List<ContentSource> sources, ExecutorService executor) {
    this.sources = sources;
    this.executor = executor;
//    this.sourceMap = sources.stream()
//        .collect(Collectors.toMap(ContentSource::getName, Function.identity()));
  }

  @Override
  public CompletableFuture<List<Article>> fetchArticles() {
    List<CompletableFuture<List<Article>>> futureArticles = sources.stream()
        .map(source -> CompletableFuture.supplyAsync(source::fetchArticles, executor))
        .toList();

    return CompletableFuture.allOf(futureArticles.toArray(new CompletableFuture[0]))
        .thenApply(v -> futureArticles.stream()
            .map(CompletableFuture::join)
            .flatMap(List::stream)
            .toList());
  }

}
