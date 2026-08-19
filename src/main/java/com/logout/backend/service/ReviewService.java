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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProfilRepository profilRepository;
    private final ReviewDTOMapper reviewDTOMapper;

    // Il faut aussi rechercher par mediaType ou je pourrais avoir des jeux et films
    // qui ressortent ( a tester)
    public List<ReviewDTO> getAllReviewOfMedia(Integer media_id) {
        return reviewRepository.findBymediaId(media_id)
                .stream()
                .map(reviewDTOMapper::tDto)
                .toList();
    }

    public void createReview(Integer profilId, ReviewDTO reviewDTO) {

        if (reviewRepository.existsByMediaIdAndProfilId(reviewDTO.mediaId(), profilId)) {
            throw new IllegalArgumentException("Le média a déjà été commenté");
        }
        Profil profilProxy = profilRepository.getReferenceById(profilId);
        Review review = reviewDTOMapper.tEntity(reviewDTO);
        review.setProfil(profilProxy);
        reviewRepository.save(review);
    }

    @Transactional
    public void updateReview(Integer id, Integer profilId, ReviewDTO reviewDTO) {
        Review reviewToUpdate = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Le commentaire est introuvable"));
        if (!reviewToUpdate.getProfil().getId().equals(profilId)) {
            throw new IllegalArgumentException("Vous n'êtes pas autorisé à modifer ce commentaire");
        }
        reviewToUpdate.setRate(reviewDTO.rate());
        reviewToUpdate.setComment(reviewDTO.comment());
        reviewRepository.save(reviewToUpdate);
    }

    public void deleteReview(Integer id) {
        if (!reviewRepository.existsById(id)) {
            throw new EntityNotFoundException("Commentaire introuvable avec l'id " + id);
        }
        reviewRepository.deleteById(id);
    }

}
