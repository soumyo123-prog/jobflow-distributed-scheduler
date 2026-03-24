package com.jobflow.scheduler.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class SchedulerThreadFactory implements ThreadFactory {
  private final AtomicInteger counter = new AtomicInteger(1);
  private final String poolName;

  public SchedulerThreadFactory(String poolName) {
    this.poolName = poolName;
  }

  @Override
  public Thread newThread(Runnable task) {
    return new Thread(task, poolName + "-thread-" + counter.getAndIncrement());
  }

}
