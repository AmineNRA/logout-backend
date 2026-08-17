package com.logout.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.logout.backend.dto.ListDTO;
import com.logout.backend.model.List;

@Mapper(componentModel = "spring")
public interface ListDTOMapper {
    @Mapping(target = "profilId", source = "profil.id")
    ListDTO tDto(List list);
}
