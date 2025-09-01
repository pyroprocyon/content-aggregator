package com.ctambaoan.aggregator.controller;

import com.ctambaoan.aggregator.entity.Article;
import com.ctambaoan.aggregator.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/articles")
public class ContentController {

  private final ContentService service;

  @GetMapping
  public ResponseEntity<List<Article>> fetchArticles(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size,
      @RequestParam(required = false) String sortBy
  ) {
    var sort = Sort.by(sortBy == null ? "id" : sortBy).ascending();
    var pageable = PageRequest.of(page, size, sort);
    return ResponseEntity.ok(service.getArticles(pageable).getContent());
  }

}
