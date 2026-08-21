package com.logout.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProfilDTO(
        Integer id,
        @Email @NotBlank String email,
        @NotBlank String pseudo,
        String description,
        String profilPicture) {

}
