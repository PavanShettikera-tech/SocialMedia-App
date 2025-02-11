package com.socialmedia.repository;


import com.socialmedia.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for managing {@link Like} entities.
 */
@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {


    /**
     * Counts the number of likes for a specified post.
     */
    Long countByPostId(Long postId);
}
