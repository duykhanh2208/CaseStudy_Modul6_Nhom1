package com.houserenting.service.impl;

import com.houserenting.model.Notification;
import com.houserenting.repository.NotificationRepository;
import com.houserenting.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Iterable<Notification> showAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Optional<Notification> findOne(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Long delete(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isPresent()){
            notification.get().setStatus("deleted");
            notificationRepository.save(notification.get());
            return id;
        }
        return null;
    }

    @Override
    public List<Notification> listUnReadNotifyByAccountLoginId(long accountId) {
        return notificationRepository.listUnReadNotifyByAccountLoginId(accountId);
    }
}
