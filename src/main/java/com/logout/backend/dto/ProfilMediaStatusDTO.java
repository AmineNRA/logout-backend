package com.logout.backend.dto;

import com.logout.backend.enums.Media;
import com.logout.backend.enums.Status;

public record ProfilMediaStatusDTO(
        Integer id,
        Media mediaType,
        Status status,
        Integer externalId,
        Integer profilId) {

}
