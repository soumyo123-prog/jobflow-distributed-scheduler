package com.jobflow.scheduler.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobflow.scheduler.entity.JobEntity;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, UUID> {

}
