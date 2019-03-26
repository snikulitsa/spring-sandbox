package com.nikulitsa.springsandbox.services;

/**
 * Интерфейс сервиса регистрации по email.
 *
 * @author Sergey Nikulitsa
 */
public interface RegistrationService {

    /**
     * Регистрация DAO пользователя по email.
     * @param mail email пользователя
     */
    void register(String mail);
}
