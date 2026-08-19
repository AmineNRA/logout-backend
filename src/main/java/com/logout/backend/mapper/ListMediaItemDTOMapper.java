package com.logout.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.logout.backend.dto.ListMediaItemDTO;
import com.logout.backend.model.ListMediaItem;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ListMediaItemDTOMapper {
    @Mapping(target = "listId", source = "list.id")
    ListMediaItemDTO tDto(ListMediaItem listMediaItem);

    @Mapping(target = "list.id", source = "listId")
    ListMediaItem tEntity(ListMediaItemDTO listMediaItemDTO);
}
