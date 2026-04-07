package com.mutualfund.backend.repo;

import com.mutualfund.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

//Repository interface for User entity
// Provides CRUD operations using Spring Data JPA
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByRole(User.Role role);
    List<User> findByStatus(User.Status status);
}