package com.logout.backend.mapper;

import org.mapstruct.Mapper;

import com.logout.backend.dto.ProfilDTO;
import com.logout.backend.model.Profil;

@Mapper(componentModel = "spring")
public interface ProfilDTOMapper {
    ProfilDTO tDto(Profil profil);

}
