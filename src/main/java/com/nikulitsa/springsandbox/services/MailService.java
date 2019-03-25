package com.nikulitsa.springsandbox.services;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

/**
 * Интерфейс сервиса по работе с email.
 *
 * @author Sergey Nikulitsa
 */
public interface MailService {

    String from();

    void send(SimpleMailMessage message) throws MailException;

    SimpleMailMessage buildMessage(String from, String subject, String text, String... to);
}
