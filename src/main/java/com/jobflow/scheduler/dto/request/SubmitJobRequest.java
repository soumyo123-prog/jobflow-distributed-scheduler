package com.jobflow.scheduler.dto.request;

import java.util.Map;

import com.jobflow.scheduler.constant.JobType;

import lombok.Data;

@Data
public class SubmitJobRequest {
  private JobType jobType;
  private Map<String, Object> payload;
}
