package com.jobflow.scheduler.dto.request;

import java.util.Map;

import com.jobflow.scheduler.constant.JobType;

import lombok.Data;
import lombok.NonNull;

@Data
public class SubmitJobRequest {
  @NonNull
  private JobType jobType;
  @NonNull
  private Map<String, Object> payload;
}
