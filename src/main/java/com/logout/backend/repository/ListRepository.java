package com.logout.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logout.backend.model.List;

@Repository
public interface ListRepository extends JpaRepository<List, Integer> {

}