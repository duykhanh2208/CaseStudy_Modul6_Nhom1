package com.houserenting.service;

import com.houserenting.model.Notification;

import java.util.List;

public interface NotificationService extends GeneralService<Notification> {
    List<Notification> listUnReadNotifyByAccountLoginId(long accountId);
}
