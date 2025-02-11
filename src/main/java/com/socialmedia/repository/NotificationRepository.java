package com.socialmedia.repository;


import com.socialmedia.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


/**
 * Repository interface for managing {@link Notification} entities.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {


    /**
     * Retrieves a list of notifications for a given user ID.
     */
    List<Notification> findByUserId(Long userId);
}
