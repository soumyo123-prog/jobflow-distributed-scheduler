package com.jobflow.scheduler.exception;

public class JobPersistenceException extends RuntimeException {
  public JobPersistenceException(String message, Throwable cause) {
    super(message, cause);
  }
}
