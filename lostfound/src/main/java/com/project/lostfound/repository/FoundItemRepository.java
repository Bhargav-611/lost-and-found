package com.project.lostfound.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lostfound.entity.FoundItem;

public interface FoundItemRepository extends JpaRepository<FoundItem, Long> {
}
