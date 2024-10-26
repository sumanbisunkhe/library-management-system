package com.library.management.service;


import com.library.management.utils.CustomEmailMessage;

public interface EmailService {
    void sendNotificationEmail(CustomEmailMessage emailMessage);
}
