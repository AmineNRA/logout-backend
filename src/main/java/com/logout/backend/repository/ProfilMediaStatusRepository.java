package com.logout.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logout.backend.model.ProfilMediaStatus;

@Repository
public interface ProfilMediaStatusRepository extends JpaRepository<ProfilMediaStatus, Integer> {

}