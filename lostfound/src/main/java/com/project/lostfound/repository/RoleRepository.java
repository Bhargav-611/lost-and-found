package com.project.lostfound.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.lostfound.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	 @Query("SELECT r FROM Role r WHERE r.name = :name") // Correct query for "role" table
	 Optional<Role> findByName(@Param("name") String name);
}
