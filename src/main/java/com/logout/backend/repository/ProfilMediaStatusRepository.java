package com.logout.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logout.backend.enums.Status;
import com.logout.backend.model.ProfilMediaStatus;

@Repository
public interface ProfilMediaStatusRepository extends JpaRepository<ProfilMediaStatus, Integer> {

    List<ProfilMediaStatus> findByProfilIdAndStatus(Integer profilId, Status status);

    Optional<ProfilMediaStatus> findByExternalIdAndProfilId(Integer externalId, Integer profilId);
}