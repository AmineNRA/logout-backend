package com.logout.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.logout.backend.dto.ReviewDTO;
import com.logout.backend.model.Review;

@Mapper(componentModel = "spring")
public interface ReviewDTOMapper {
    @Mapping(target = "profilId", source = "profil.id")
    ReviewDTO tDto(Review review);

}
