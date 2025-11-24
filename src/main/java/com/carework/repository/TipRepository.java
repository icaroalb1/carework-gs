package com.carework.repository;

import com.carework.model.Tip;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TipRepository extends JpaRepository<Tip, UUID> {
}
