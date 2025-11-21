package com.carework.repository;

import com.carework.model.CheckinEventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckinEventLogRepository extends JpaRepository<CheckinEventLog, Long> {
}
