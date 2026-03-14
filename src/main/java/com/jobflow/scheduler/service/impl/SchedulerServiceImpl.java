package com.jobflow.scheduler.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobflow.scheduler.constant.JobStatus;
import com.jobflow.scheduler.constant.JobType;
import com.jobflow.scheduler.dto.request.SubmitJobRequest;
import com.jobflow.scheduler.dto.response.GetJobStatusResponse;
import com.jobflow.scheduler.dto.response.SubmitJobResponse;
import com.jobflow.scheduler.entity.JobEntity;
import com.jobflow.scheduler.repository.JobRepository;
import com.jobflow.scheduler.service.SchedulerService;

@Service
public class SchedulerServiceImpl implements SchedulerService {
  @Autowired
  private JobRepository jobRepository;

  @Override
  public SubmitJobResponse submitJob(SubmitJobRequest submitJobRequest) {
    JobEntity job = new JobEntity();

    job.setJobType(JobType.EMAIL);
    job.setPayload(submitJobRequest.getPayload());
    job.setStatus(JobStatus.PENDING);

    this.jobRepository.save(job);

    return SubmitJobResponse.fromJobEntity(job);
  }

  @Override
  public GetJobStatusResponse getJobStatusJob(UUID jobId) {
    Optional<JobEntity> job = jobRepository.findById(jobId);

    if (!job.isPresent()) {
      return null;
    }
    return GetJobStatusResponse.fromJobEntity(job.get());
  }

}
