package com.jobflow.scheduler.service;

import com.jobflow.scheduler.dto.request.SubmitJobRequest;
import com.jobflow.scheduler.dto.response.SubmitJobResponse;

public interface SchedulerService {
  SubmitJobResponse submitJob(SubmitJobRequest submitJobRequest);
}
