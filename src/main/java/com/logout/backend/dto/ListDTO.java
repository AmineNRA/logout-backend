package com.logout.backend.dto;

import com.logout.backend.enums.Media;

import jakarta.validation.constraints.NotBlank;

public record ListDTO(
                Integer id,
                @NotBlank String name,
                Media mediaType,
                Integer profilId) {

}
