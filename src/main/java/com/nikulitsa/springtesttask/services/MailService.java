package com.nikulitsa.springtesttask.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

public interface MailService {

    String from();

    void send(SimpleMailMessage message) throws MailException;

    SimpleMailMessage buildMessage(String from, String subject, String text, String... to);
}
