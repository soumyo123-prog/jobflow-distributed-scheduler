package com.jobflow.scheduler.worker;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.jobflow.scheduler.entity.JobEntity;
import com.jobflow.scheduler.executor.JobExecutor;
import com.jobflow.scheduler.executor.JobExecutorRegistry;
import com.jobflow.scheduler.repository.JobRepository;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobConsumer {
  @Autowired
  private StringRedisTemplate redisTemplate;

  @Autowired
  private ExecutorService executorService;

  @Autowired
  private JobExecutorRegistry jobExecutorRegistry;

  @Autowired
  private JobRepository jobRepository;

  @Value("${scheduler.redis.queue.name}")
  private String queueName;

  @Value("${scheduler.redis.queue.timeoutMs}")
  private Integer timeoutMs;

  private volatile boolean running = true;

  @EventListener(ApplicationReadyEvent.class)
  public void consume() {
    Thread consumerThread = new Thread(() -> {
      while (running) {
        String jobId = this.redisTemplate.opsForList().rightPop(queueName, Duration.ofMillis(timeoutMs));
        if (jobId == null) {
          continue;
        }
        this.executorService.submit(() -> {
          log.info("Processing job: {} on thread: {}", jobId, Thread.currentThread().getName());

          UUID jobIdUuid = UUID.fromString(jobId);
          JobEntity job = jobRepository.findById(jobIdUuid).get();

          JobExecutor jobExecutor = jobExecutorRegistry.getExecutor(job.getJobType());
          try {
            jobExecutor.execute(job);
          } catch (Exception e) {
            log.error(
                String.format("Error while processing job: {} on thread: {}", jobId, Thread.currentThread().getName()),
                e);
          }
        });
      }
    });

    consumerThread.setDaemon(true);
    consumerThread.start();
  }

  @PreDestroy
  public void shutDown() {
    this.running = false;
  }
}
