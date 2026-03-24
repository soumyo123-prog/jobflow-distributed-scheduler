package com.jobflow.scheduler.executor;

import com.jobflow.scheduler.constant.JobType;
import com.jobflow.scheduler.entity.JobEntity;

public interface JobExecutor {
  JobType getJobType();

  void execute(JobEntity job) throws Exception;
}
