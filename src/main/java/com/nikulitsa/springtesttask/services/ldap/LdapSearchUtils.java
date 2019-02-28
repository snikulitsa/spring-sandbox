package com.nikulitsa.springtesttask.services.ldap;

import org.springframework.ldap.support.LdapUtils;
import org.springframework.util.Base64Utils;

import javax.naming.Name;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.function.Supplier;

public final class LdapSearchUtils {

    public final static String EMPTY_BASE_STRING = "";
    public final static Name EMPTY_BASE_NAME = LdapUtils.newLdapName("");

    private LdapSearchUtils() {
    }

    public static boolean searchSuccess(List list) {
        return list != null && !list.isEmpty();
    }

    public static String rfcObjectGUID (byte[] objectGUID) {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < objectGUID.length; i++) {
            stringBuilder
                .append("\\")
                .append(prefixZeros((int) objectGUID[i] & 0xFF));
        }
        return stringBuilder.toString();
    }

    private static String prefixZeros(int value) {
        if (value <= 0xF) {
            return "0" + Integer.toHexString(value);
        } else {
            return Integer.toHexString(value);
        }
    }

    public static Supplier<EntityNotFoundException> entityNotFoundExceptionSupplier(byte[] objectGUID) {
        return () -> new EntityNotFoundException(
            "Entity with objectGUID: " +
                Base64Utils.encodeToString(objectGUID) +
                " not found"
        );
    }
}
