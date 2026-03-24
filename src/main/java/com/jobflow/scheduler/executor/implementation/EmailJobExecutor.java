package com.jobflow.scheduler.executor.implementation;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.jobflow.scheduler.constant.JobType;
import com.jobflow.scheduler.entity.JobEntity;
import com.jobflow.scheduler.executor.JobExecutor;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailJobExecutor implements JobExecutor {
  @Override
  public JobType getJobType() {
    return JobType.EMAIL;
  }

  @Override
  public void execute(JobEntity job) throws Exception {
    Map<String, Object> payload = job.getPayload();

    String to = (String) payload.get("to");
    String subject = (String) payload.get("subject");
    String body = (String) payload.get("body");

    if (to == null) {
      throw new IllegalArgumentException("Email job requires 'to' field in payload");
    }

    log.info("Sending email to: {}, subject: {}, body: {}", to, subject, body);
    Thread.sleep(2000);

    if (Math.random() <= 0.2) {
      log.error("Failed to send email to: {}", to);
      throw new RuntimeException("Simulated email delivery failure");
    }

    log.info("Email sent successfully to: {}", to);
  }

}
