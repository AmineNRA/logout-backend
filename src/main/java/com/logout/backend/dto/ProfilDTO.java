package com.logout.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProfilDTO(
        Integer id,
        @Email String email,
        @NotBlank String pseudo) {

}
