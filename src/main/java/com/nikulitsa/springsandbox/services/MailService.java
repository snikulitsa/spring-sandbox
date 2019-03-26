package com.nikulitsa.springsandbox.services;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

/**
 * Интерфейс сервиса по работе с email.
 *
 * @author Sergey Nikulitsa
 */
public interface MailService {

    /**
     * Отправитель.
     */
    String from();

    /**
     * Отправить сообщение.
     *
     * @param message сообщение
     * @throws MailException если происходит ошибка при отправке
     */
    void send(SimpleMailMessage message) throws MailException;

    /**
     * Сконструировать сообщение.
     *
     * @param from    отправитель
     * @param subject тема письма
     * @param text    текст письма
     * @param to      адреса назначения
     * @return {@link SimpleMailMessage}
     */
    SimpleMailMessage buildMessage(String from, String subject, String text, String... to);
}
