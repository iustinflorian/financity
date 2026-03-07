package com.gifprojects.financity.repository;

import com.gifprojects.financity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User getUserById(Long id);
    boolean existsByUsername(String username);
    User findByEmail(String email);
}
