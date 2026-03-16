package com.jobflow.scheduler.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jobflow.scheduler.exception.JobDispatchException;
import com.jobflow.scheduler.exception.JobPersistenceException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  record ErrorResponse(String error, String message) {}

  @ExceptionHandler(JobPersistenceException.class)
  public ResponseEntity<ErrorResponse> handleJobPersistenceException(JobPersistenceException e) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ErrorResponse("JOB_PERSISTENCE_FAILED", e.getMessage()));
  }

  @ExceptionHandler(JobDispatchException.class)
  public ResponseEntity<ErrorResponse> handleJobDispatchException(JobDispatchException e) {
    return ResponseEntity
        .status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(new ErrorResponse("JOB_DISPATCH_FAILED", e.getMessage()));
  }
}
