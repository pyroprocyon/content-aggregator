package com.ctambaoan.aggregator.connector;

import com.ctambaoan.aggregator.model.Article;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public abstract class ContentSource {

  private final String name;

  public abstract List<Article> fetchArticles();
}
