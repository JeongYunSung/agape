package com.yunseong.core.notification.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = "select n from Notification n inner join fetch n.receiver where n.receiver.email = :email",
        countQuery = "select count(n) from Notification n where n.receiver.email = :email")
    Page<Notification> findAllByEmail(String email, Pageable pageable);
}
