package com.jobflow.scheduler.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jobflow.scheduler.factory.SchedulerThreadFactory;

@Configuration
public class ThreadPoolConfig {
  @Value("${scheduler.thread.pool.name}")
  private String poolName;

  @Value("${scheduler.thread.core.pool.size}")
  private Integer corePoolSize;

  @Value("${scheduler.thread.max.pool.size}")
  private Integer maxPoolSize;

  @Value("${scheduler.thread.keep.alive.time}")
  private Integer keepAliveTime;

  @Bean
  public ExecutorService jobExecutor() {
    return new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(100), new SchedulerThreadFactory(poolName), new CallerRunsPolicy());
  }
}
