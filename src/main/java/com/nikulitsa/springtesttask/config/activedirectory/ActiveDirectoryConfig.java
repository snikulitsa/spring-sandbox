package com.nikulitsa.springtesttask.config.activedirectory;

import com.nikulitsa.springtesttask.config.activedirectory.properties.AbstractActiveDirectoryProperties;
import com.nikulitsa.springtesttask.config.activedirectory.properties.ActiveDirectoryProperties_1;
import com.nikulitsa.springtesttask.config.activedirectory.properties.ActiveDirectoryProperties_2;
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
public class ActiveDirectoryConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ActiveDirectoryConfig.class);

    private static final String BINARY_ATTRIBUTES_KEY = "java.naming.ldap.attributes.binary";
    private static final String TIMEOUT_KEY = "com.sun.jndi.ldap.connect.timeout";
    public static final String LDAP_TEMPLATE_PREFIX = "ldapTemplate_";
    public static final String ACTIVE_DIRECTORY_PROPERTIES_PREFIX = "activeDirectoryProperties_";
    public static final String LDAP_CONTEXT_SOURCE_PREFIX = "ldapContextSource_";
    public static final String LDAP_USER_DETAILS_CONTEXT_MAPPER_PREFIX = "userDetailsContextMapper_";

    public static final int[] LDAP_CONTEXT_NUMBERS = {1, 2};
    private final ActiveDirectoryProperties_1 activeDirectoryProperties_1;
    private final ActiveDirectoryProperties_2 activeDirectoryProperties_2;

    private final ApplicationContext applicationContext;

    public ActiveDirectoryConfig(ActiveDirectoryProperties_1 activeDirectoryProperties_1,
                                 ActiveDirectoryProperties_2 activeDirectoryProperties_2,
                                 ApplicationContext applicationContext) {
        this.activeDirectoryProperties_1 = activeDirectoryProperties_1;
        this.activeDirectoryProperties_2 = activeDirectoryProperties_2;
        this.applicationContext = applicationContext;
    }

    //LDAP context_1
    @Bean(name = LDAP_CONTEXT_SOURCE_PREFIX + 1)
    public LdapContextSource ldapContextSource_1() {
        return getLdapContextSource(activeDirectoryProperties_1);
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
        return getLdapContextSource(activeDirectoryProperties_2);
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

    private LdapContextSource getLdapContextSource(AbstractActiveDirectoryProperties ldapProperties) {
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl(ldapProperties.getUrl());
        if (ldapProperties.isEnabled()) {
            configureLdapContext(ldapContextSource, ldapProperties);
        }
        return ldapContextSource;
    }

    private void configureLdapContext(LdapContextSource ldapContextSource,
                                      AbstractActiveDirectoryProperties ldapProperties) {

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

    public String getBase(int adNumber) {
        return getActiveDirectoryPropertiesFromContext(adNumber).getBase();
    }

    public String getUserSearchFilter(int adNumber) {
        return getActiveDirectoryPropertiesFromContext(adNumber)
            .getUser()
            .getSearch_filter();
    }

    public boolean isKerberosEnabled() {
        boolean kerberosEnabled = getActiveDirectoryPropertiesFromContext(KerberosConfig.KERBEROS_AD_CONTEXT)
            .getKerberos()
            .isEnabled();

        boolean ldapEnabled = isLdapEnabled(KerberosConfig.KERBEROS_AD_CONTEXT);

        if (kerberosEnabled && !ldapEnabled) {
            throw new IllegalStateException("If KERBEROS enabled ACTIVE DIRECTORY_1 must be enabled too.");
        }

        return kerberosEnabled;
    }

    public boolean isLdapEnabled(int adNumber) {
        return getActiveDirectoryPropertiesFromContext(adNumber).isEnabled();
    }

    public AbstractActiveDirectoryProperties getActiveDirectoryPropertiesFromContext(int adNumber) {
        return (AbstractActiveDirectoryProperties) applicationContext
            .getBean(ACTIVE_DIRECTORY_PROPERTIES_PREFIX + adNumber);
    }

    public LdapTemplate getLdapTemplateFromContext(int adNumber) {
        return (LdapTemplate) applicationContext.getBean(LDAP_TEMPLATE_PREFIX + adNumber);
    }

    public LdapContextSource getLdapContextSourceFromContext(int adNumber) {
        return (LdapContextSource) applicationContext.getBean(LDAP_CONTEXT_SOURCE_PREFIX + adNumber);
    }

    public UserDetailsContextMapper getUserDetailsContextMapperFromContext(int adNumber) {
        return (UserDetailsContextMapper) applicationContext
            .getBean(LDAP_USER_DETAILS_CONTEXT_MAPPER_PREFIX + adNumber);
    }
}
