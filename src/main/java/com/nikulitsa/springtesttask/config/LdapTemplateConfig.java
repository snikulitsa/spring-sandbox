package com.nikulitsa.springtesttask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.ldap.SpringSecurityLdapTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class LdapTemplateConfig {

    private final String BINARY_ATTRIBUTES_KEY = "java.naming.ldap.attributes.binary";
    private final String TIMEOUT = "com.sun.jndi.ldap.connect.timeout";

    @Value("${ldap.url}")
    private String url;

    @Value("${ldap.base}")
    private String base;

    @Value("${ldap.user.dn}")
    private String userDN;

    @Value("${ldap.user.password}")
    private String password;

    @Value("${ldap.referral.mode}")
    private String referralMode;

    @Value("${ldap.binary.attributes}")
    private String binaryAttributes;

    @Bean(name = "ldapTemplate")
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(ldapContextSource());
    }

    @Bean
    public SpringSecurityLdapTemplate springSecurityLdapTemplate() {
        return new SpringSecurityLdapTemplate(ldapContextSource());
    }

    @Bean(name = "contextSource")
    public LdapContextSource ldapContextSource() {

        Map<String, Object> baseEnvironmentProperties = new HashMap<>();
        baseEnvironmentProperties.put(BINARY_ATTRIBUTES_KEY, binaryAttributes);
        baseEnvironmentProperties.put(TIMEOUT, "50000");

        LdapContextSource ldapContextSource = new LdapContextSource();
//        ldapContextSource.setUrl("ldap://192.168.1.243:389/dc=dca,dc=ccsss,dc=ru");
//        ldapContextSource.setBase("");
//        ldapContextSource.setUserDn("CN=Administrator,OU=Users,DC=CCSSS,DC=RU");
//        ldapContextSource.setPassword("qweqwe123$");
////        ldapContextSource.setReferral(referralMode);
//        ldapContextSource.afterPropertiesSet();
////        ldapContextSource.setBaseEnvironmentProperties(baseEnvironmentProperties);

        ldapContextSource.setUrl(url);
        ldapContextSource.setBase(base);
        ldapContextSource.setUserDn(userDN);
        ldapContextSource.setPassword(password);
        ldapContextSource.setReferral(referralMode);
        ldapContextSource.afterPropertiesSet();
        ldapContextSource.setBaseEnvironmentProperties(baseEnvironmentProperties);
        return ldapContextSource;
    }

    public String getBase() {
        return this.base;
    }
}
