package com.socialmedia.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a like. 
 * Overriding toString() to exclude post/user.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime likedAt;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    @PrePersist
    protected void onCreate() {
        this.likedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        // Tests expect "Like(id=1, likedAt=null)"
        return "Like(id=" + id +
               ", likedAt=" + likedAt + ")";
    }
}
