package com.campusops.backend.service;

import java.util.List;

import com.campusops.backend.dto.EquipmentResponse;
import com.campusops.backend.repository.EquipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Transactional(readOnly = true)
    public List<EquipmentResponse> findAll() {
        return equipmentRepository.findAll()
                .stream()
                .map(equipment -> new EquipmentResponse(
                        equipment.getId(),
                        equipment.getName(),
                        equipment.getCategory(),
                        equipment.getLocationId(),
                        equipment.getStatus()
                ))
                .toList();
    }
}