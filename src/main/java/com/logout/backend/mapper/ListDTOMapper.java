package com.logout.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.logout.backend.dto.ListDTO;
import com.logout.backend.model.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ListDTOMapper {
    @Mapping(target = "profilId", source = "profil.id")
    ListDTO tDto(List list);

    @Mapping(target = "profil.id", source = "profilId")
    List tEntity(ListDTO listDTO);
}