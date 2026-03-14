package com.jobflow.scheduler.service;

import java.util.UUID;

import com.jobflow.scheduler.dto.request.SubmitJobRequest;
import com.jobflow.scheduler.dto.response.GetJobStatusResponse;
import com.jobflow.scheduler.dto.response.SubmitJobResponse;

public interface SchedulerService {
  SubmitJobResponse submitJob(SubmitJobRequest submitJobRequest);

  GetJobStatusResponse getJobStatusJob(UUID jobId);
}
