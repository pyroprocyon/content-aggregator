package com.ctambaoan.aggregator.service;

import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ctambaoan.aggregator.connector.NewsFetcher;
import com.ctambaoan.aggregator.dto.ArticleDto;
import com.ctambaoan.aggregator.repository.ArticleRepository;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContentServiceTest {

  @Mock
  private ArticleRepository mockArticleRepository;

  @Mock
  private NewsFetcher newsFetcher;

  @InjectMocks
  private ContentServiceImpl contentService;

  @Test
  void cacheData_shouldSaveNewArticlesAndIgnoreDuplicates() {
    ArticleDto newTechArticle = TestDataFactory.createMockArticleDto("TECHNOLOGY", "tech.com/new");
    ArticleDto existingBusinessArticle = TestDataFactory.createMockArticleDto("BUSINESS",
        "business.com/existing");

    when(newsFetcher.fetchArticles()).thenReturn(
        CompletableFuture.completedFuture(List.of(newTechArticle, existingBusinessArticle)));
    when(mockArticleRepository.existsByUrl("tech.com/new")).thenReturn(false);
    when(mockArticleRepository.existsByUrl("business.com/existing")).thenReturn(true);

    contentService.cacheData();

    verify(mockArticleRepository, times(1)).save(argThat(savedArticle ->
        "tech.com/new".equals(savedArticle.getUrl())
    ));
    verify(mockArticleRepository, never()).save(argThat(article ->
        "business.com/existing".equals(article.getUrl())
    ));
  }
}
