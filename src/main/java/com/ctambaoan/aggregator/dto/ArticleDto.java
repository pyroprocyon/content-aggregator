package com.ctambaoan.aggregator.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ArticleDto {

  private Source source;
  private String category;
  private String author;
  private String title;
  private String description;
  private String url;
  private LocalDateTime publishedAt;
}
