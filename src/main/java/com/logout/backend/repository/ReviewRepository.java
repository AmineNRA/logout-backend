package com.logout.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.logout.backend.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findBymediaId(Integer id);

    boolean existsByMediaIdAndProfilId(Integer mediaId, Integer profilId);

}