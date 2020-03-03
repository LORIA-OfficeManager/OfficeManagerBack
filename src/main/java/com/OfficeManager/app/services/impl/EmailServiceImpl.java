package com.OfficeManager.app.services.impl;

import com.OfficeManager.app.services.interfaces.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceImpl implements IEmailService {

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("officemanager@yopmail.fr");
        message.setSubject("Erreur OfficeManager");
        message.setText(text);
        emailSender.send(message);
    }
}
