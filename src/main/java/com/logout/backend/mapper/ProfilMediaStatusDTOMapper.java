package com.logout.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.logout.backend.dto.ProfilMediaStatusDTO;
import com.logout.backend.model.ProfilMediaStatus;

@Mapper(componentModel = "spring")
public interface ProfilMediaStatusDTOMapper {
    @Mapping(target = "profilId", source = "profil.id")
    ProfilMediaStatusDTO tDto(ProfilMediaStatus profilMediaStatus);

    @Mapping(target = "profil.id", source = "profilId")
    ProfilMediaStatus tEntity(ProfilMediaStatusDTO profilMediaStatusDTO);

}
