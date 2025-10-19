package com.example.cabs.repository.impl;

import com.example.cabs.dto.NotificationDto;
import com.example.cabs.repository.NotificationMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotificationRepositoryImpl {
    private final NotificationMapper mapper;

    public NotificationRepositoryImpl(NotificationMapper mapper) {
        this.mapper = mapper;
    }

    public Integer notificationsCountUnread(Integer userId) {
        return mapper.notificationsCountUnread(userId);
    }

    public List<NotificationDto> notificationsListForUser(Integer userId, Boolean onlyUnread, Integer top) {
        return mapper.notificationsListForUser(userId, onlyUnread, top);
    }

    public void notificationsDelete(Integer userId, Long notificationId) {
        mapper.notificationsDelete(userId, notificationId);
    }

    public void notificationsMarkRead(Integer userId, Long notificationId) {
        mapper.notificationsMarkRead(userId, notificationId);
    }

    public void notificationsMarkAll(Integer userId) {
        mapper.notificationsMarkAll(userId);
    }

    public void notifyCreate(String type) {
        mapper.notifyCreate(type);
    }
}
