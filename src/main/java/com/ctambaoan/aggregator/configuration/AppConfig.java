package com.ctambaoan.aggregator.configuration;

import com.ctambaoan.aggregator.connector.NewsSourceEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@Configuration
@EnableAsync
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class AppConfig {

  @Bean
  public Executor contentFetcherExecutor() {
    return Executors.newFixedThreadPool(NewsSourceEnum.values().length);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
