package com.jobflow.scheduler.service.implementation;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.jobflow.scheduler.constant.JobStatus;
import com.jobflow.scheduler.entity.JobEntity;
import com.jobflow.scheduler.executor.JobExecutor;
import com.jobflow.scheduler.executor.JobExecutorRegistry;
import com.jobflow.scheduler.repository.JobRepository;
import com.jobflow.scheduler.service.JobProcessingService;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultJobProcessingService implements JobProcessingService {
  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private JobExecutorRegistry jobExecutorRegistry;

  @Override
  public void processJob(String jobId) {
    log.info("Processing job: {} on thread: {}", jobId, Thread.currentThread().getName());

    UUID jobUuid = UUID.fromString(jobId);

    Optional<JobEntity> maybeJob = jobRepository.findById(jobUuid);

    if (!maybeJob.isPresent()) {
      log.warn("Job with ID: " + jobId + " not present in the database!");
      return;
    }

    JobEntity job = maybeJob.get();

    if (job.getStatus() != JobStatus.PENDING) {
      log.warn("Job with ID: " + jobId + " has already been run!");
      return;
    }

    job.setStartedAt(LocalDateTime.now());
    job.setStatus(JobStatus.RUNNING);
    job.setWorkerThread(Thread.currentThread().getName());

    jobRepository.save(job);
    log.info("Starting job execution with ID: " + jobId);

    JobExecutor jobExecutor = jobExecutorRegistry.getExecutor(job.getJobType());

    try {
      jobExecutor.execute(job);

      job.setStatus(JobStatus.SUCCESS);
      job.setCompletedAt(LocalDateTime.now());

      jobRepository.save(job);
      log.info("Completed job execution with ID: " + jobId);
    } catch (Exception e) {
      job.setStatus(JobStatus.FAILED);
      job.setErrorMessage(e.getMessage());

      jobRepository.save(job);
      log.error("Error while processing job: {} on thread: {}", jobId, Thread.currentThread().getName(), e);
    }
  }

}
