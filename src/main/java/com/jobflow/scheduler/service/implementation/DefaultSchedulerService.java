package com.jobflow.scheduler.service.implementation;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.dao.DataAccessException;

import com.jobflow.scheduler.constant.JobStatus;
import com.jobflow.scheduler.dispatcher.JobDispatcher;
import com.jobflow.scheduler.dto.request.SubmitJobRequest;
import com.jobflow.scheduler.dto.response.GetJobStatusResponse;
import com.jobflow.scheduler.dto.response.SubmitJobResponse;
import com.jobflow.scheduler.entity.JobEntity;
import com.jobflow.scheduler.exception.JobDispatchException;
import com.jobflow.scheduler.exception.JobPersistenceException;
import com.jobflow.scheduler.repository.JobRepository;
import com.jobflow.scheduler.service.SchedulerService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultSchedulerService implements SchedulerService {
  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private JobDispatcher jobDispatcher;

  @Override
  public SubmitJobResponse submitJob(SubmitJobRequest submitJobRequest) {
    JobEntity job = new JobEntity();

    job.setJobType(submitJobRequest.getJobType());
    job.setPayload(submitJobRequest.getPayload());
    job.setStatus(JobStatus.PENDING);

    try {
      this.jobRepository.save(job);
      log.info("Job saved to DB: " + job.getId());
    } catch (DataAccessException e) {
      log.error("Failed to persist job to database", e);
      throw new JobPersistenceException("Failed to persist job to database", e);
    }

    try {
      this.jobDispatcher.enqueueJob(job.getId());
      log.info("Job added to queue: " + job.getId());
    } catch (RuntimeException e) {
      log.error("Job saved but failed to enqueue: " + job.getId(), e);
      throw new JobDispatchException("Job saved but failed to enqueue: " + job.getId(), e);
    }

    return SubmitJobResponse.fromJobEntity(job);
  }

  @Override
  public GetJobStatusResponse getJobStatus(UUID jobId) {
    Optional<JobEntity> job = jobRepository.findById(jobId);

    if (!job.isPresent()) {
      return null;
    }
    return GetJobStatusResponse.fromJobEntity(job.get());
  }

}
