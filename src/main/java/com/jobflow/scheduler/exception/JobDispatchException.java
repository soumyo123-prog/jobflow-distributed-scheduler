package com.jobflow.scheduler.exception;

public class JobDispatchException extends RuntimeException {
  public JobDispatchException(String message, Throwable cause) {
    super(message, cause);
  }
}
