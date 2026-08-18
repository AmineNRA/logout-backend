package com.logout.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.logout.backend.dto.ReviewDTO;
import com.logout.backend.model.Review;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewDTOMapper {

    @Mapping(target = "profilId", source = "profil.id")
    @Mapping(target = "profilPseudo", source = "profil.pseudo")
    ReviewDTO tDto(Review review);

    @Mapping(target = "profil.id", source = "profilId")
    Review tEntity(ReviewDTO reviewDTO);

}
