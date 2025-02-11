package com.socialmedia.dto;


import lombok.*;


/**
 * Data Transfer Object for managing posts.
 *
 * <p>
 * <strong>Constraints:</strong>
 * - title, content should not be empty
 * - userId must reference an existing user
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {


    private Long id;
    private String title;
    private String content;
    private Long userId;
}
