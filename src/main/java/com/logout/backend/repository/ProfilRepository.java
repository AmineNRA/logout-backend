package com.logout.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logout.backend.model.Profil;

@Repository
public interface ProfilRepository extends JpaRepository<Profil, Integer> {

    boolean existsByEmail(String email);

    boolean existsByPseudo(String pseudo);

    Optional<Profil> findByPseudo(String pseudo);

}
