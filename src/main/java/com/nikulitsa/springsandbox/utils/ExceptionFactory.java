package com.nikulitsa.springsandbox.utils;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityNotFoundException;
import java.util.function.Supplier;

/**
 * Утилитный класс-фабрика исключений.
 *
 * @author Sergey Nikulitsa
 */
public final class ExceptionFactory {

    private ExceptionFactory() {
    }

    /**
     * {@link Supplier<EntityNotFoundException>} генерирующий исключение, если сущность не найдена по objectGUID.
     *
     * @param clazz      класс сущности
     * @param objectGUID objectGUID сущности
     * @return {@link Supplier<EntityNotFoundException>}
     */
    public static Supplier<EntityNotFoundException> entityNotFoundExceptionSupplier(Class<?> clazz,
                                                                                    byte[] objectGUID) {
        return () -> new EntityNotFoundException(
            clazz.getSimpleName() + " with objectGUID: " + Base64Utils.encodeToString(objectGUID) + " not found."
        );
    }

    /**
     * {@link Supplier<EntityNotFoundException>} генерирующий исключение, если сущность не найдена по objectGUID.
     *
     * @param clazz класс сущности
     * @param dn    Distinguished Name
     * @return {@link Supplier<EntityNotFoundException>}
     */
    public static Supplier<EntityNotFoundException> entityNotFoundExceptionSupplier(Class<?> clazz,
                                                                                    String dn) {
        return () -> new EntityNotFoundException(clazz.getSimpleName() + " with DN: " + dn + " not found.");
    }

    /**
     * {@link Supplier<UsernameNotFoundException>} генерирующий исключение, если пользователь не найден по имени.
     *
     * @param username имя пользователя
     * @return {@link Supplier<UsernameNotFoundException>}
     */
    public static Supplier<UsernameNotFoundException> usernameNotFoundExceptionSupplier(String username) {
        return () -> new UsernameNotFoundException("User: " + username + " does not exist");
    }
}
