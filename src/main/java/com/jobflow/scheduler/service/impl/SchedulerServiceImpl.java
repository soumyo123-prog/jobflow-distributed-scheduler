package com.jobflow.scheduler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobflow.scheduler.constant.JobStatus;
import com.jobflow.scheduler.constant.JobType;
import com.jobflow.scheduler.dto.request.SubmitJobRequest;
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

    SubmitJobResponse response = new SubmitJobResponse();

    response.setJobId(job.getId());
    response.setStatus(job.getStatus());

    return response;
  }

}
