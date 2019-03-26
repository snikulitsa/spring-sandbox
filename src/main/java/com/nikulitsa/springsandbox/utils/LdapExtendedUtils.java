package com.nikulitsa.springsandbox.utils;

import org.springframework.ldap.support.LdapUtils;

import javax.naming.Name;
import java.util.List;

/**
 * Утилитный класс для работы с LDAP.
 *
 * @author Sergey Nikulitsa
 */
public final class LdapExtendedUtils {

    public static final String EMPTY_BASE_STRING = "";
    public static final Name EMPTY_BASE_NAME = LdapUtils.newLdapName("");

    private LdapExtendedUtils() {
    }

    /**
     * Проверка прошел ли поиск в LDAP успешно.
     *
     * @param list результат поиска
     * @return успех/провал
     */
    public static boolean searchSuccess(List list) {
        return list != null && !list.isEmpty();
    }

    /**
     * Преобразование бинарного objectGUID в строку, которую можно передать в LDAP-запрос.
     *
     * @param objectGUID бинарный objectGUID
     * @return objectGUID в строки
     */
    public static String objectGUIDToString(byte[] objectGUID) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < objectGUID.length; i++) {
            stringBuilder
                .append("\\")
                .append(prefixZeros((int) objectGUID[i] & 0xFF));
        }
        return stringBuilder.toString();
    }

    /**
     * Добавление ведущих нулей в строку с objectGIUD.
     *
     * @param value байт, для которого будет добавляться префикс (если необходимо)
     * @return байт в виде строки
     */
    private static String prefixZeros(int value) {
        if (value <= 0xF) {
            return "0" + Integer.toHexString(value);
        } else {
            return Integer.toHexString(value);
        }
    }
}
