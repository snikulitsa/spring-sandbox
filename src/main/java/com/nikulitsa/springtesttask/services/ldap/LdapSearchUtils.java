package com.nikulitsa.springtesttask.services.ldap;

import java.util.List;

public final class LdapSearchUtils {

    private LdapSearchUtils() {
    }

    public static boolean searchSuccess(List list) {
        return list != null && !list.isEmpty();
    }

    public static String getGUID(byte[] objectGUID) {
        StringBuilder guid = new StringBuilder();
        for (int i = 0; i < objectGUID.length; i++) {
            StringBuilder dblByte = new StringBuilder(Integer.toHexString(objectGUID[i] & 0xff));
            if (dblByte.length() == 1) {
                guid.append("0");
            }
            guid.append(dblByte);
        }
        return guid.toString();
    }

    public static String getObjectGUID(byte[] guidBytes) {
        return String.format(
            "%02x%02x%02x%02x-%02x%02x-%02x%02x-%02x%02x-%02x%02x%02x%02x%02x%02x",
            guidBytes[3] & 255,
            guidBytes[2] & 255,
            guidBytes[1] & 255,
            guidBytes[0] & 255,
            guidBytes[5] & 255,
            guidBytes[4] & 255,
            guidBytes[7] & 255,
            guidBytes[6] & 255,
            guidBytes[8] & 255,
            guidBytes[9] & 255,
            guidBytes[10] & 255,
            guidBytes[11] & 255,
            guidBytes[12] & 255,
            guidBytes[13] & 255,
            guidBytes[14] & 255,
            guidBytes[15] & 255
        );
    }

    public static String converObjectGUID(byte[] objectGUID) {
        return new StringBuilder("G-")
            .append(prefixZeros((int) objectGUID[3] & 0xFF))
            .append(prefixZeros((int) objectGUID[2] & 0xFF))
            .append(prefixZeros((int) objectGUID[1] & 0xFF))
            .append(prefixZeros((int) objectGUID[0] & 0xFF))
            .append("-")
            .append(prefixZeros((int) objectGUID[5] & 0xFF))
            .append(prefixZeros((int) objectGUID[4] & 0xFF))
            .append("-")
            .append(prefixZeros((int) objectGUID[7] & 0xFF))
            .append(prefixZeros((int) objectGUID[6] & 0xFF))
            .append("-")
            .append(prefixZeros((int) objectGUID[8] & 0xFF))
            .append(prefixZeros((int) objectGUID[9] & 0xFF))
            .append("-")
            .append(prefixZeros((int) objectGUID[10] & 0xFF))
            .append(prefixZeros((int) objectGUID[11] & 0xFF))
            .append(prefixZeros((int) objectGUID[12] & 0xFF))
            .append(prefixZeros((int) objectGUID[13] & 0xFF))
            .append(prefixZeros((int) objectGUID[14] & 0xFF))
            .append(prefixZeros((int) objectGUID[15] & 0xFF))
            .toString();
    }

    private static String prefixZeros(int value) {
        if (value <= 0xF) {
            return "0" + Integer.toHexString(value);
        } else {
            return Integer.toHexString(value);
        }
    }
}
