package com.nikulitsa.springsandbox.config.activedirectory.properties;

/**
 * Часть настроек Active Directory, в которой производится кастомизация аттрибутов.
 *
 * @author Sergey Nikulitsa
 */
public class Attributes {

    /**
     * Аттрибуты, которые должны передаваться в бинарном виде.
     */
    private String binaryAttributes;

    public String getBinaryAttributes() {
        return binaryAttributes;
    }

    public Attributes setBinaryAttributes(String binaryAttributes) {
        this.binaryAttributes = binaryAttributes;
        return this;
    }
}
