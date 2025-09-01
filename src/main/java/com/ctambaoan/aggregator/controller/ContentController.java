package com.ctambaoan.aggregator.controller;

import com.ctambaoan.aggregator.entity.Article;
import com.ctambaoan.aggregator.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/articles")
public class ContentController {

  private final ContentService service;

  @GetMapping
  public ResponseEntity<Page<Article>> fetchArticles(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDir,
      @RequestParam(required = false) String category
  ) {
    if (page < 0) page = 0;
    if (size < 1) size = 1;
    if (size > 100) size = 100;

    Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
    var sort = Sort.by(direction, sortBy);
    var pageable = PageRequest.of(page, size, sort);

    var result = (category == null || category.isBlank()) ?
        service.getArticles(pageable) :
        service.getArticlesByCategory(category, pageable);

    return ResponseEntity.ok()
        .header("X-Total-Count", String.valueOf(result.getTotalElements()))
        .body(result);
  }

}
