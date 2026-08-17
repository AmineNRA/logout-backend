package com.logout.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.logout.backend.dto.ListMediaItemDTO;
import com.logout.backend.model.ListMediaItem;

@Mapper(componentModel = "spring")
public interface ListMediaItemDTOMapper {
    @Mapping(target = "listId", source = "list.id")
    ListMediaItemDTO tDto(ListMediaItem listMediaItem);
}
