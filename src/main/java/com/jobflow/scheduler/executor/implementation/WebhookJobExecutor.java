package com.jobflow.scheduler.executor.implementation;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.jobflow.scheduler.constant.JobType;
import com.jobflow.scheduler.entity.JobEntity;
import com.jobflow.scheduler.executor.JobExecutor;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WebhookJobExecutor implements JobExecutor {

  @Override
  public JobType getJobType() {
    return JobType.WEBHOOK;
  }

  @Override
  public void execute(JobEntity job) throws Exception {
    Map<String, Object> payload = job.getPayload();
    String url = (String) payload.get("url");

    if (url == null) {
      throw new IllegalArgumentException("Webhook job requires 'url' field in payload");
    }

    log.info("Sending webhook to: {}", url);
    Thread.sleep(3000);
    log.info("Webhook delivered to: {}", url);
  }

}
