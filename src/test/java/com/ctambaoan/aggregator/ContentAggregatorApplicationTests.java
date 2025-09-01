package com.ctambaoan.aggregator;

import com.ctambaoan.aggregator.entity.Article;
import com.ctambaoan.aggregator.repository.ArticleRepository;
import com.ctambaoan.aggregator.service.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ContentAggregatorApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ArticleRepository repository;

  @BeforeEach
  void setup() {
    repository.deleteAll();
  }

  @Test
  void testGetArticles() throws Exception {
    Article testArticle = new Article(TestDataFactory
        .createMockArticleDto("TECHNOLOGY", "technology.com"));
    repository.save(testArticle);

    mockMvc.perform(get("/api/articles"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.totalElements", is(1)))
        .andExpect(jsonPath("$.totalPages", is(1)))
        .andExpect(jsonPath("$.size", is(20))) // Assuming default size
        .andExpect(jsonPath("$.content", hasSize(1)))
        .andExpect(jsonPath("$.content[0].category", is("TECHNOLOGY")))
        .andExpect(jsonPath("$.content[0].url", is("technology.com")));
  }

}
