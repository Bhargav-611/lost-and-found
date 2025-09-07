package com.project.lostfound.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lostfound.entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
}