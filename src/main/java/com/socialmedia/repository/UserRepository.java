package com.socialmedia.repository;


import com.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


/**
 * Repository interface for managing {@link User} entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    /**
     * Retrieves a user by their unique email address.
     */
    Optional<User> findByEmail(String email);
}
