package com.socialmedia.dto;


import lombok.*;


/**
 * Data Transfer Object for managing likes.
 *
 * <p><strong>Constraints:</strong>
 * - postId, userId must reference valid post/user (checked in Service).
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeDTO {


    private Long id;
    private Long postId;
    private Long userId;
}
