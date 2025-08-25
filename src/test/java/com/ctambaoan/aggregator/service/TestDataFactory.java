package com.ctambaoan.aggregator.service;

import com.ctambaoan.aggregator.dto.ArticleDto;
import com.ctambaoan.aggregator.dto.Source;

import java.time.LocalDateTime;

public class TestDataFactory {

  public static ArticleDto createMockArticleDto(String category, String url) {
    Source mockSource = new Source();
    mockSource.setId(category + "-news");
    mockSource.setName(category.substring(0, 1).toUpperCase() + category.substring(1) + " News");

    ArticleDto dto = new ArticleDto();
    dto.setSource(mockSource); // Set the non-null Source
    dto.setCategory(category);
    dto.setAuthor("Mock Author");
    dto.setTitle("A mock title for " + category);
    dto.setDescription("A mock description.");
    dto.setUrl(url);
    dto.setPublishedAt(LocalDateTime.now());

    return dto;
  }

}
