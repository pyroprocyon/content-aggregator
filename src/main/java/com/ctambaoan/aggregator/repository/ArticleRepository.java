package com.ctambaoan.aggregator.repository;

import com.ctambaoan.aggregator.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

  boolean existsByUrl(String url);

  Page<Article> findByCategoryIgnoreCase(String category, Pageable pageable);
}
