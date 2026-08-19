package com.logout.backend.dto;

import com.logout.backend.enums.Media;
import com.logout.backend.enums.Status;

import jakarta.validation.constraints.NotNull;

public record ProfilMediaStatusDTO(
        Integer id,
        @NotNull Media mediaType,
        @NotNull Status status,
        Integer mediaId,
        Integer profilId) {

}
