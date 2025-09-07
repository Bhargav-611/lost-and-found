package com.project.lostfound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.lostfound.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
