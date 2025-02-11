package com.socialmedia.service;

import com.socialmedia.dto.LikeDTO;
import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.model.Like;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.repository.LikeRepository;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public LikeDTO likePost(LikeDTO likeDTO) {
        Post post = postRepository.findById(likeDTO.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + likeDTO.getPostId()));
        User user = userRepository.findById(likeDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + likeDTO.getUserId()));

        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        Like saved = likeRepository.save(like);

        return mapToDTO(saved);
    }

    public Long getLikesCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    public void unlikePost(Long id) {
        Like existingLike = likeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Like not found with id " + id));
        likeRepository.delete(existingLike);
    }

    // Public so the test can call it
    public LikeDTO mapToDTO(Like like) {
        LikeDTO dto = new LikeDTO();
        dto.setId(like.getId());
        dto.setPostId(like.getPost().getId());
        dto.setUserId(like.getUser().getId());
        return dto;
    }
}
