package com.socialmedia.service;

import com.socialmedia.dto.PostDTO;
import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired private PostRepository postRepository;
    @Autowired private UserRepository userRepository;

    public PostDTO createPost(PostDTO postDTO) {
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + postDTO.getUserId()));

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setUser(user);

        Post saved = postRepository.save(post);
        return new PostDTO(saved.getId(), saved.getTitle(), saved.getContent(), saved.getUser().getId());
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(p -> new PostDTO(p.getId(), p.getTitle(), p.getContent(), p.getUser().getId()))
                .collect(Collectors.toList());
    }

    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));
        return new PostDTO(post.getId(), post.getTitle(), post.getContent(), post.getUser().getId());
    }

    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setUpdatedAt(LocalDateTime.now());

        Post updated = postRepository.save(post);
        return new PostDTO(updated.getId(), updated.getTitle(), updated.getContent(), updated.getUser().getId());
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));
        postRepository.delete(post);
    }
}
