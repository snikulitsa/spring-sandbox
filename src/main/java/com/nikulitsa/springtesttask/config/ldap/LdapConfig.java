package com.nikulitsa.springtesttask.config.ldap;

import com.nikulitsa.springtesttask.config.ldap.properties.AbstractLdapProperties;
import com.nikulitsa.springtesttask.config.ldap.properties.LdapProperties_1;
import com.nikulitsa.springtesttask.config.ldap.properties.LdapProperties_2;
import com.nikulitsa.springtesttask.services.ldap.LdapMapperFabric;
import com.nikulitsa.springtesttask.services.ldap.LdapQueryFabric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class LdapConfig {

    private static final Logger LOG = LoggerFactory.getLogger(LdapConfig.class);

    private static final String BINARY_ATTRIBUTES_KEY = "java.naming.ldap.attributes.binary";
    private static final String TIMEOUT_KEY = "com.sun.jndi.ldap.connect.timeout";
    public static final String LDAP_TEMPLATE_PREFIX = "ldapTemplate_";
    public static final String LDAP_PROPERTIES_PREFIX = "ldapProperties_";
    public static final String LDAP_CONTEXT_SOURCE_PREFIX = "ldapContextSource_";
    public static final String LDAP_USER_DETAILS_CONTEXT_MAPPER_PREFIX = "userDetailsContextMapper_";

    public static final int[] LDAP_CONTEXT_NUMBERS = {1, 2};
    private final LdapProperties_1 ldapProperties_1;
    private final LdapProperties_2 ldapProperties_2;

    private final ApplicationContext applicationContext;

    public LdapConfig(LdapProperties_1 ldapProperties_1,
                      LdapProperties_2 ldapProperties_2,
                      ApplicationContext applicationContext) {
        this.ldapProperties_1 = ldapProperties_1;
        this.ldapProperties_2 = ldapProperties_2;
        this.applicationContext = applicationContext;
    }

    //LDAP context_1
    @Bean(name = LDAP_CONTEXT_SOURCE_PREFIX + 1)
    public LdapContextSource ldapContextSource_1() {
        return getLdapContextSource(ldapProperties_1);
    }

    @Bean(name = LDAP_TEMPLATE_PREFIX + 1)
    public LdapTemplate ldapTemplate_1() {
        return new LdapTemplate(ldapContextSource_1());
    }

    @Bean(name = LDAP_USER_DETAILS_CONTEXT_MAPPER_PREFIX + 1)
    @Autowired
    public UserDetailsContextMapper userDetailsContextMapper_1(LdapQueryFabric ldapQueryFabric,
                                                               LdapMapperFabric ldapMapperFabric) {

        return new LdapUserDetailsContextMapper(ldapTemplate_1(), ldapQueryFabric, ldapMapperFabric);
    }

    //LDAP context_2
    @Bean(name = LDAP_CONTEXT_SOURCE_PREFIX + 2)
    public LdapContextSource ldapContextSource_2() {
        return getLdapContextSource(ldapProperties_2);
    }

    @Bean(name = LDAP_TEMPLATE_PREFIX + 2)
    public LdapTemplate ldapTemplate_2() {
        return new LdapTemplate(ldapContextSource_2());
    }

    @Bean(name = LDAP_USER_DETAILS_CONTEXT_MAPPER_PREFIX + 2)
    @Autowired
    public UserDetailsContextMapper userDetailsContextMapper_2(LdapQueryFabric ldapQueryFabric,
                                                               LdapMapperFabric ldapMapperFabric) {

        return new LdapUserDetailsContextMapper(ldapTemplate_2(), ldapQueryFabric, ldapMapperFabric);
    }

    private LdapContextSource getLdapContextSource(AbstractLdapProperties ldapProperties) {
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl(ldapProperties.getUrl());
        if (ldapProperties.isEnabled()) {
            configureLdapContext(ldapContextSource, ldapProperties);
        }
        return ldapContextSource;
    }

    private void configureLdapContext(LdapContextSource ldapContextSource, AbstractLdapProperties ldapProperties) {

        Map<String, Object> baseEnvironmentProperties = new HashMap<>();
        baseEnvironmentProperties.put(BINARY_ATTRIBUTES_KEY, ldapProperties.getAttributes().getBinary_attributes());
        baseEnvironmentProperties.put(TIMEOUT_KEY, ldapProperties.getTimeout());

        ldapContextSource.setBase(ldapProperties.getBase());
        ldapContextSource.setUserDn(ldapProperties.getUser().getDn());
        ldapContextSource.setPassword(ldapProperties.getUser().getPassword());
        ldapContextSource.setReferral(ldapProperties.getReferral_mode());
        ldapContextSource.afterPropertiesSet();
        ldapContextSource.setBaseEnvironmentProperties(baseEnvironmentProperties);
    }

    public String getBase(int contextNumber) {
        return getLdapPropertiesFromContext(contextNumber).getBase();
    }

    public String getUserSearchFilter(int contextNumber) {
        return getLdapPropertiesFromContext(contextNumber)
            .getUser()
            .getSearch_filter();
    }

    public boolean isLdapEnabled(int contextNumber) {
        return getLdapPropertiesFromContext(contextNumber).isEnabled();
    }

    public AbstractLdapProperties getLdapPropertiesFromContext(int contextNumber) {
        return (AbstractLdapProperties) applicationContext.getBean(LDAP_PROPERTIES_PREFIX + contextNumber);
    }

    public LdapTemplate getLdapTemplateFromContext(int ldapContextNumber) {
        return (LdapTemplate) applicationContext.getBean(LDAP_TEMPLATE_PREFIX + ldapContextNumber);
    }

    public LdapContextSource getLdapContextSourceFromContext(int ldapContextNumber) {
        return (LdapContextSource) applicationContext.getBean(LDAP_CONTEXT_SOURCE_PREFIX + ldapContextNumber);
    }

    public UserDetailsContextMapper getUserDetailsContextMapperFromContext(int ldapContextNumber) {
        return (UserDetailsContextMapper) applicationContext
            .getBean(LDAP_USER_DETAILS_CONTEXT_MAPPER_PREFIX + ldapContextNumber);
    }
}
