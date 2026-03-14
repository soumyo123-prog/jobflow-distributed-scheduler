package com.jobflow.scheduler.dto.response;

import java.util.UUID;

import com.jobflow.scheduler.constant.JobStatus;
import com.jobflow.scheduler.entity.JobEntity;

import lombok.Data;

@Data
public class SubmitJobResponse {
  private UUID jobId;
  private JobStatus status;

  public static SubmitJobResponse fromJobEntity(JobEntity jobEntity) {
    SubmitJobResponse response = new SubmitJobResponse();

    response.setJobId(jobEntity.getId());
    response.setStatus(jobEntity.getStatus());

    return response;
  }
}
