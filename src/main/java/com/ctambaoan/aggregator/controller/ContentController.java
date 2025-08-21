package com.ctambaoan.aggregator.controller;

import com.ctambaoan.aggregator.model.Article;
import com.ctambaoan.aggregator.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/articles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContentController {

  private final ContentService service;

  @GetMapping
  public CompletableFuture<List<Article>> fetchArticles() {
    return service.fetchArticles();
  }

}
