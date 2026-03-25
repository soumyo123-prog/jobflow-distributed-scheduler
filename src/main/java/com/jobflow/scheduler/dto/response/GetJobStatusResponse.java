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
  private LocalDateTime startedAt;
  private LocalDateTime completedAt;
  private String errorMessage;
  private String workerThread;

  public static GetJobStatusResponse fromJobEntity(JobEntity jobEntity) {
    GetJobStatusResponse response = new GetJobStatusResponse();

    response.setJobId(jobEntity.getId());
    response.setCreatedAt(jobEntity.getCreatedAt());
    response.setJobStatus(jobEntity.getStatus());
    response.setJobType(jobEntity.getJobType());
    response.setStartedAt(jobEntity.getStartedAt());
    response.setCompletedAt(jobEntity.getCompletedAt());
    response.setErrorMessage(jobEntity.getErrorMessage());
    response.setWorkerThread(jobEntity.getWorkerThread());

    return response;
  }
}
