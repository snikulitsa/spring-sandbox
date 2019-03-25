package com.nikulitsa.springsandbox.services.impl;

import com.nikulitsa.springsandbox.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Nikulitsa
 * @see MailService
 */
@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public String from() {
        return from;
    }

    @Override
    public void send(SimpleMailMessage message) throws MailException {
        mailSender.send(message);
    }

    @Override
    public SimpleMailMessage buildMessage(String author,
                                          String subject,
                                          String text,
                                          String... to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(author);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }
}
