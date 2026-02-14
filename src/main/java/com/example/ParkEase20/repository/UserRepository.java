package com.example.ParkEase20.repository;

import com.example.ParkEase20.entity.User;
import com.example.ParkEase20.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User>findByUserRole(UserRole userRole);
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    Optional<User> findAllByUserRole(UserRole userRole);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
long  countByUserRole(UserRole userRole);
}
