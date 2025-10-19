package com.example.cabs.service.impl;

import com.example.cabs.dto.NotificationDto;
import com.example.cabs.repository.impl.NotificationRepositoryImpl;
import com.example.cabs.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepositoryImpl repo;

    public NotificationServiceImpl(NotificationRepositoryImpl repo) {
        this.repo = repo;
    }

    @Override
    public int countUnread(Integer userId) {
        Integer r = repo.notificationsCountUnread(userId);
        return r == null ? 0 : r;
    }

    @Override
    public List<NotificationDto> listForUser(Integer userId, Boolean onlyUnread, Integer top) {
        return repo.notificationsListForUser(userId, onlyUnread, top);
    }

    @Override
    @Transactional
    public void delete(Integer userId, Long notificationId) {
        repo.notificationsDelete(userId, notificationId);
    }

    @Override
    @Transactional
    public void markRead(Integer userId, Long notificationId) {
        repo.notificationsMarkRead(userId, notificationId);
    }

    @Override
    @Transactional
    public void markAll(Integer userId) {
        repo.notificationsMarkAll(userId);
    }

    @Override
    @Transactional
    public void create(String type) {
        repo.notifyCreate(type);
    }
}
