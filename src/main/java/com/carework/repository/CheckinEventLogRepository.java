package com.carework.repository;

import com.carework.model.CheckinEventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CheckinEventLogRepository extends JpaRepository<CheckinEventLog, UUID> {
}
