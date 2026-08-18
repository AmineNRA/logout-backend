package com.logout.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

import com.logout.backend.enums.Media;

public record ReviewDTO(
        Integer id,
        String comment,
        @Min(1) @Max(5) Integer rate,
        @NotNull Media mediaType,
        @NotNull Integer externalId,
        Integer profilId,
        String profilPseudo,
        Date updated) {

}
