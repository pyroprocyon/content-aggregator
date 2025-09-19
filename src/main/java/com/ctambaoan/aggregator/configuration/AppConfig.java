package com.ctambaoan.aggregator.configuration;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class AppConfig {

  @Bean
  public ExecutorService contentFetcherExecutor() {
    return Executors.newVirtualThreadPerTaskExecutor();
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
