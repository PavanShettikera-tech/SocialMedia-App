package com.socialmedia.dto;


import lombok.*;


/**
 * Data Transfer Object for managing comments.
 *
 * <p>
 * <strong>Constraints:</strong>
 * - content not null or empty
 * - postId, userId must reference existing records (enforced in Service).
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {


    private Long id;
    private String content;
    private Long postId;
    private Long userId;
}
