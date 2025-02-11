package com.socialmedia.repository;


import com.socialmedia.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


/**
 * Repository for managing {@link Comment} entities.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    /**
     * Retrieves a list of comments for a given post ID.
     */
    List<Comment> findByPostId(Long postId);
}
