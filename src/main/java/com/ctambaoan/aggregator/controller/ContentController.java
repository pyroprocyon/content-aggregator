package com.ctambaoan.aggregator.controller;

import com.ctambaoan.aggregator.entity.Article;
import com.ctambaoan.aggregator.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/articles")
public class ContentController {

  private final ContentService service;

  @GetMapping
  public ResponseEntity<List<Article>> fetchArticles() {
    return ResponseEntity.ok(service.getArticles());
  }

}
