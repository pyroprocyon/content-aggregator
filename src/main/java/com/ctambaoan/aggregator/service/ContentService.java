package com.ctambaoan.aggregator.service;

import com.ctambaoan.aggregator.model.Article;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ContentService {

  CompletableFuture<List<Article>> fetchArticles();

}
