package com.example.cabs.service;

import com.example.cabs.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    int countUnread(Integer userId);
    List<NotificationDto> listForUser(Integer userId, Boolean onlyUnread, Integer top);
    void delete(Integer userId, Long notificationId);
    void markRead(Integer userId, Long notificationId);
    void markAll(Integer userId);
    void create(String type);
}
