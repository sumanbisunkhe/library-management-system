package com.library.management.service.impl;

import com.library.management.dto.NotificationDto;
import com.library.management.entities.Notification;
import com.library.management.entities.User;
import com.library.management.repo.NotificationRepo;
import com.library.management.repo.UserRepo;
import com.library.management.service.EmailService;
import com.library.management.service.NotificationService;
import com.library.management.utils.CustomEmailMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Override
    public NotificationDto createNotification(NotificationDto notificationDto) {
        // Convert DTO to Entity
        Notification notification = modelMapper.map(notificationDto, Notification.class);

        // Set additional fields
        notification.setCreatedAt(LocalDateTime.now());
        notification.setIsRead(false);

        // Optional: Check if user exists
        User user = userRepo.findById(notificationDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        notification.setUser(user);

        // Save the notification
        notification = notificationRepo.save(notification);

        /// Prepare the email message
        CustomEmailMessage emailMessage = new CustomEmailMessage();
        emailMessage.setFrom("readymade090@gmail.com");
        emailMessage.setTo(user.getEmail());
        emailMessage.setSentDate(new Date());
        emailMessage.setSubject("New Notification");
        emailMessage.setText(notificationDto.getMessage());

        // Send email notification to the user
        emailService.sendNotificationEmail(emailMessage);
        // Convert back to DTO
        return modelMapper.map(notification, NotificationDto.class);
    }

    @Override
    public List<NotificationDto> getNotificationsByUserId(Long userId) {
        List<Notification> notifications = notificationRepo.findByUserId(userId);
        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDto markAsRead(Long id) {
        Notification notification = notificationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setIsRead(true);
        notification = notificationRepo.save(notification);

        return modelMapper.map(notification, NotificationDto.class);
    }

    @Override
    public void deleteNotification(Long id) {
        Optional<Notification> notificationOptional = notificationRepo.findById(id);
        if (notificationOptional.isPresent()) {
            notificationRepo.delete(notificationOptional.get());
        } else {
            throw new RuntimeException("Notification not found");
        }
    }
}
