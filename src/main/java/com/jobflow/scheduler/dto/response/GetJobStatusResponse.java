package com.jobflow.scheduler.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.jobflow.scheduler.constant.JobStatus;
import com.jobflow.scheduler.constant.JobType;
import com.jobflow.scheduler.entity.JobEntity;

import lombok.Data;

@Data
public class GetJobStatusResponse {
  private UUID jobId;
  private JobType jobType;
  private JobStatus jobStatus;
  private LocalDateTime createdAt;

  public static GetJobStatusResponse fromJobEntity(JobEntity jobEntity) {
    GetJobStatusResponse response = new GetJobStatusResponse();

    response.setJobId(jobEntity.getId());
    response.setCreatedAt(jobEntity.getCreatedAt());
    response.setJobStatus(jobEntity.getStatus());
    response.setJobType(jobEntity.getJobType());

    return response;
  }
}
