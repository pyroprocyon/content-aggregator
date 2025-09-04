package com.ctambaoan.aggregator.entity;

import com.ctambaoan.aggregator.dto.ArticleDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String source;
  private String category;
  private String author;
  private String title;
  @Column(length = 500)
  private String description;
  @Column(unique = true)
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
