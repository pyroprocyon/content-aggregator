package com.ctambaoan.aggregator.connector;

import com.ctambaoan.aggregator.dto.ArticleDto;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface NewsFetcher {

  CompletableFuture<List<ArticleDto>> fetchArticles();
}
