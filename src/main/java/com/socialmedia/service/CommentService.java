package com.socialmedia.service;

import com.socialmedia.dto.CommentDTO;
import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.model.Comment;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.repository.CommentRepository;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing comments.
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public CommentDTO createComment(CommentDTO commentDTO) {
        // Removed colon from "Post not found with id"
        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + commentDTO.getPostId()));

        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + commentDTO.getUserId()));

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);

        return new CommentDTO(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getPost().getId(),
                savedComment.getUser().getId()
        );
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
                .map(c -> new CommentDTO(c.getId(), c.getContent(), c.getPost().getId(), c.getUser().getId()))
                .collect(Collectors.toList());
    }

    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Comment existing = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));
        existing.setContent(commentDTO.getContent());
        Comment updated = commentRepository.save(existing);

        return new CommentDTO(
                updated.getId(),
                updated.getContent(),
                updated.getPost().getId(),
                updated.getUser().getId()
        );
    }

    public void deleteComment(Long id) {
        Comment existing = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));
        commentRepository.delete(existing);
    }
}
