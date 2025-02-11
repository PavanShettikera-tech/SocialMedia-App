package com.socialmedia.repository;


import com.socialmedia.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for managing {@link Post} entities.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // Additional custom queries if needed
}
