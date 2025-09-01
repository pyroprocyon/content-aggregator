package com.ctambaoan.aggregator.service;

import com.ctambaoan.aggregator.connector.NewsApiClient;
import com.ctambaoan.aggregator.connector.NewsSourceEnum;
import com.ctambaoan.aggregator.dto.ArticleDto;
import com.ctambaoan.aggregator.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContentServiceTest {

  @Mock
  private ArticleRepository mockArticleRepository;
  @Mock
  private NewsApiClient mockNewsApiClient;

  private ContentServiceImpl contentService;

  @BeforeEach
  void setUp() {
    Executor sameThreadExecutor = Runnable::run;
    contentService = new ContentServiceImpl(mockNewsApiClient, sameThreadExecutor, mockArticleRepository);
  }

  @Test
  void cacheData_shouldSaveNewArticlesAndIgnoreDuplicates() {
    ArticleDto newTechArticle = TestDataFactory.createMockArticleDto("TECHNOLOGY", "tech.com/new");
    ArticleDto existingBusinessArticle = TestDataFactory.createMockArticleDto("BUSINESS", "business.com/existing");

    when(mockArticleRepository.existsByUrl("tech.com/new")).thenReturn(false);
    when(mockArticleRepository.existsByUrl("business.com/existing")).thenReturn(true);

    for (NewsSourceEnum category : NewsSourceEnum.values()) {
      switch (category) {
        case TECHNOLOGY:
          when(mockNewsApiClient.fetchArticles(category)).thenReturn(List.of(newTechArticle));
          break;
        case BUSINESS:
          when(mockNewsApiClient.fetchArticles(category)).thenReturn(List.of(existingBusinessArticle));
          break;
        default:
          // For all other categories, return an empty list.
          when(mockNewsApiClient.fetchArticles(category)).thenReturn(Collections.emptyList());
          break;
      }
    }

    contentService.cacheData();

    verify(mockArticleRepository, times(1)).save(argThat(savedArticle ->
        "tech.com/new".equals(savedArticle.getUrl())
    ));
    verify(mockArticleRepository, never()).save(argThat(article ->
        "business.com/existing".equals(article.getUrl())
    ));
  }
}
