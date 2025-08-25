package com.ctambaoan.aggregator.service;

import com.ctambaoan.aggregator.connector.BusinessNewsSource;
import com.ctambaoan.aggregator.connector.ContentSource;
import com.ctambaoan.aggregator.connector.TechnologyNewsSource;
import com.ctambaoan.aggregator.dto.ArticleDto;
import com.ctambaoan.aggregator.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.Executor;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContentServiceTest {

  @Mock
  private ArticleRepository mockArticleRepository;
  @Mock
  private TechnologyNewsSource mockTechSource;
  @Mock
  private BusinessNewsSource mockBusinessSource;

  private ContentServiceImpl contentService;

  @BeforeEach
  void setUp() {
    Executor sameThreadExecutor = Runnable::run;
    List<ContentSource> sources = List.of(mockTechSource, mockBusinessSource);
    contentService = new ContentServiceImpl(sources, sameThreadExecutor, mockArticleRepository);
  }

  @Test
  void cacheData_shouldSaveNewArticlesAndIgnoreDuplicates() {
    ArticleDto newArticleDto = TestDataFactory.createMockArticleDto("technology", "new-url");
    ArticleDto existingArticleDto = TestDataFactory.createMockArticleDto("business", "existing-url");

    when(mockTechSource.fetchArticles()).thenReturn(List.of(newArticleDto));
    when(mockBusinessSource.fetchArticles()).thenReturn(List.of(existingArticleDto));
    when(mockArticleRepository.existsByUrl("new-url")).thenReturn(false);
    when(mockArticleRepository.existsByUrl("existing-url")).thenReturn(true);

    contentService.cacheData();

    verify(mockArticleRepository).save(argThat(savedArticle ->
        "new-url".equals(savedArticle.getUrl())
    ));
    verify(mockArticleRepository, never()).save(argThat(existingArticle ->
        "existing-url".equals(existingArticle.getUrl())));
  }
}
