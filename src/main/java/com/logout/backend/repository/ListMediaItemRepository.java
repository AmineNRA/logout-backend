package com.logout.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logout.backend.model.ListMediaItem;

@Repository
public interface ListMediaItemRepository extends JpaRepository<ListMediaItem, Integer> {

}