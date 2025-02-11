package com.socialmedia.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing media content. 
 * Overriding toString() to exclude post references.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String type;
    private LocalDateTime uploadedAt;

    @ManyToOne
    private Post post;

    @PrePersist
    protected void onCreate() {
        this.uploadedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        // Tests expect "Media(id=1, url=..., type=..., uploadedAt=...)"
        return "Media(id=" + id +
               ", url=" + url +
               ", type=" + type +
               ", uploadedAt=" + uploadedAt + ")";
    }
}
