package com.logout.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import com.logout.backend.enums.Media;

public record ReviewDTO(
        Integer id,
        String comment,
        @Min(1) @Max(5) Integer rate,
        Media mediaType,
        Integer externalId,
        Integer profilId) {

}
