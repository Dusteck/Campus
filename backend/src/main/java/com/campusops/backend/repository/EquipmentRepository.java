package com.campusops.backend.repository;

import com.campusops.backend.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository
        extends JpaRepository<Equipment, Long> {
}