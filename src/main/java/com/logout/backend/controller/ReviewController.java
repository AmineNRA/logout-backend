package com.logout.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.logout.backend.configuration.JwtUtils;
import com.logout.backend.dto.ReviewDTO;
import com.logout.backend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtUtils jwtUtils;

    // ajouter un put
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "{mediaId}")
    public List<ReviewDTO> getReviewsFromMedia(@PathVariable Integer mediaId) {
        return reviewService.getAllReviewOfMedia(mediaId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Void> createReview(
            @Valid @RequestBody ReviewDTO reviewDTO,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Integer profilId = jwtUtils.extractProfilId(token);

        reviewService.createReview(profilId, reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "{id}")
    public void updateReview(@PathVariable Integer id, @RequestBody ReviewDTO reviewDTO,
            @RequestHeader("Authorization") String autHeader) {
        String token = autHeader.substring(7);
        Integer profilId = jwtUtils.extractProfilId(token);
        reviewService.updateReview(id, profilId, reviewDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{id}")
    public void deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
    }

}
