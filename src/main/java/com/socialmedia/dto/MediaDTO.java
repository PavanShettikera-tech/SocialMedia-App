package com.socialmedia.dto;


import lombok.*;


/**
 * Data Transfer Object for managing media files attached to posts.
 *
 * <p><strong>Constraints:</strong>
 * - url must be a valid location if used
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaDTO {


    private Long id;
    private String url;
    private String type;
    private Long postId;
}
