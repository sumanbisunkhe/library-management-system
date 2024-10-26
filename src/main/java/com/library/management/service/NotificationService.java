package com.library.management.service;

import com.library.management.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    NotificationDto createNotification(NotificationDto notificationDto);
    List<NotificationDto> getNotificationsByUserId(Long userId);
    NotificationDto markAsRead(Long id);
    void deleteNotification(Long id);
}
