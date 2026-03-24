package com.jobflow.scheduler.exception;

public class UnsupportedJobTypeException extends RuntimeException {
  public UnsupportedJobTypeException(String message) {
    super(message);
  }
}
