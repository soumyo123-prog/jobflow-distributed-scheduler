package com.jobflow.scheduler.executor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jobflow.scheduler.constant.JobType;
import com.jobflow.scheduler.exception.UnsupportedJobTypeException;

import jakarta.annotation.PostConstruct;

@Component
public class JobExecutorRegistry {
  @Autowired
  private List<JobExecutor> jobExecutors;

  private Map<String, JobExecutor> jobTypeToExecutorMap;

  @PostConstruct
  public void init() {
    this.jobTypeToExecutorMap = new HashMap<>();
    this.jobExecutors.forEach(
        jobExecutor -> jobTypeToExecutorMap.put(jobExecutor.getJobType().name(), jobExecutor));
  }

  public JobExecutor getExecutor(JobType jobType) {
    JobExecutor executor = jobTypeToExecutorMap.get(jobType.name());
    if (executor == null) {
      throw new UnsupportedJobTypeException("No executor found for job type: " + jobType);
    }
    return executor;
  }
}
