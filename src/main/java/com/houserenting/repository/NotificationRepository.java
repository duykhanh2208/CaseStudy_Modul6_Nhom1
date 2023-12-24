package com.houserenting.repository;

import com.houserenting.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("select n from Notification n where n.receiver.id = :accountLoginId and n.status like 'unRead'")
    List<Notification> listUnReadNotifyByAccountLoginId(@Param("accountLoginId") long accountLoginId);
}
