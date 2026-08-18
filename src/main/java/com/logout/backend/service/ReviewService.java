package com.logout.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.logout.backend.dto.ReviewDTO;
import com.logout.backend.mapper.ReviewDTOMapper;
import com.logout.backend.model.Profil;
import com.logout.backend.model.Review;
import com.logout.backend.repository.ProfilRepository;
import com.logout.backend.repository.ReviewRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProfilRepository profilRepository;
    private final ReviewDTOMapper reviewDTOMapper;

    // Creer une modification de review

    public List<ReviewDTO> getAllReviewOfMedia(Integer media_id) {
        return reviewRepository.findByExternalId(media_id)
                .stream()
                .map(reviewDTOMapper::tDto)
                .toList();
    }

    public void createReview(Integer profilId, ReviewDTO reviewDTO) {
        Profil profilProxy = profilRepository.getReferenceById(profilId);
        Review review = reviewDTOMapper.tEntity(reviewDTO);
        review.setProfil(profilProxy);
        reviewRepository.save(review);
    }

    public void deleteReview(Integer id) {
        if (!profilRepository.existsById(id)) {
            throw new EntityNotFoundException("Commentaire introuvable avec l'id " + id);
        }
        reviewRepository.deleteById(id);
    }

}
