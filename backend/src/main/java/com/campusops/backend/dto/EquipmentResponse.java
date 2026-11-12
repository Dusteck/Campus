package com.campusops.backend.dto;

public record EquipmentResponse(
        Long id,
        String name,
        String category,
        Long locationId,
        String status
) {
}