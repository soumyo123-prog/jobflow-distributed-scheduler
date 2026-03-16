package com.jobflow.scheduler.dispatcher;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobDispatcher {
  @Autowired
  private StringRedisTemplate redisTemplate;

  @Value("${scheduler.redis.queue.name}")
  private String queueName;

  public void enqueueJob(UUID jobId) {
    this.redisTemplate.opsForList().leftPush(queueName, jobId.toString());
  }
}
