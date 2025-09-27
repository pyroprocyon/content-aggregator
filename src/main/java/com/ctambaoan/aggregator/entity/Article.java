package com.ctambaoan.aggregator.entity;

import com.ctambaoan.aggregator.dto.ArticleDto;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Article {

  @Id
  private long id;
  private String source;
  private String category;
  private String author;
  private String title;
  private String description;
  private String url;
  private LocalDateTime publishedAt;

  public Article(ArticleDto dto) {
    this.source = dto.getSource().getName();
    this.category = dto.getCategory();
    this.author = dto.getAuthor();
    this.title = dto.getTitle();
    this.description = dto.getDescription();
    this.url = dto.getUrl();
    this.publishedAt = dto.getPublishedAt();
  }
}
