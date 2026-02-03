package com.healthcare.healthcare_system.dto;

public record ErrorResponse(
        int status,
        String message
) {
}
