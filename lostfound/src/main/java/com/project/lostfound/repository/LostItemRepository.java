package com.project.lostfound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.lostfound.entity.LostItem;

public interface LostItemRepository extends JpaRepository<LostItem, Long> {
}
