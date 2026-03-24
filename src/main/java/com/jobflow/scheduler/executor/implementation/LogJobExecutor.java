package com.jobflow.scheduler.executor.implementation;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.jobflow.scheduler.constant.JobType;
import com.jobflow.scheduler.entity.JobEntity;
import com.jobflow.scheduler.executor.JobExecutor;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LogJobExecutor implements JobExecutor {

  @Override
  public JobType getJobType() {
    return JobType.LOG;
  }

  @Override
  public void execute(JobEntity job) throws Exception {
    Map<String, Object> payload = job.getPayload();
    String message = (String) payload.get("message");

    if (message == null) {
      throw new IllegalArgumentException("Log job requires 'message' field in payload");
    }

    log.info("[LOG JOB] {}", message);
  }

}
