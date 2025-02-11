package com.socialmedia.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    @PrePersist
    public void onCreate() {
        // Use a single variable to ensure exact equality
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Comment(id=" + id +
               ", content=" + content +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt + ")";
    }
}
