package com.jobflow.scheduler.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobflow.scheduler.dto.request.SubmitJobRequest;
import com.jobflow.scheduler.dto.response.GetJobStatusResponse;
import com.jobflow.scheduler.dto.response.SubmitJobResponse;
import com.jobflow.scheduler.service.SchedulerService;

@RestController
@RequestMapping("/api/v1")
public class SchedulerRestController {
  @Autowired
  private SchedulerService schedulerService;

  @PostMapping("/jobs")
  public ResponseEntity<SubmitJobResponse> submitJob(@RequestBody SubmitJobRequest requestBody) {
    SubmitJobResponse response = this.schedulerService.submitJob(requestBody);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/jobs/{jobId}")
  public ResponseEntity<GetJobStatusResponse> getJobStatus(@PathVariable UUID jobId) {
    GetJobStatusResponse response = this.schedulerService.getJobStatusJob(jobId);

    if (response == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(response);
  }
}
