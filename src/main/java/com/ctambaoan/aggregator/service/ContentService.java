package com.ctambaoan.aggregator.service;

import com.ctambaoan.aggregator.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ContentService {

  Page<Article> getArticles(PageRequest pageable);
}
