package com.jobflow.scheduler.dto.response;

import java.util.UUID;

import com.jobflow.scheduler.constant.JobStatus;

import lombok.Data;

@Data
public class SubmitJobResponse {
  private UUID jobId;
  private JobStatus status;
}
