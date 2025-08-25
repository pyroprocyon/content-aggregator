package com.ctambaoan.aggregator.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AppConfig {

  @Bean
  public Executor contentFetcherExecutor() {
    return Executors.newFixedThreadPool(3);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
