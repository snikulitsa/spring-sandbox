package com.nikulitsa.springsandbox.config.activedirectory;

import com.nikulitsa.springsandbox.config.activedirectory.properties.ActiveDirectoryProperties;
import com.nikulitsa.springsandbox.services.ldap.LdapMapperFactory;
import com.nikulitsa.springsandbox.services.ldap.LdapQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.kerberos.authentication.KerberosServiceAuthenticationProvider;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosTicketValidator;
import org.springframework.security.kerberos.client.config.SunJaasKrb5LoginConfig;
import org.springframework.security.kerberos.client.ldap.KerberosLdapContextSource;
import org.springframework.security.kerberos.web.authentication.SpnegoAuthenticationProcessingFilter;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.LdapUserDetailsService;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурационный класс для интеграции с Active Directory.
 *
 * @author Sergey Nikulitsa
 */
@Configuration
public class ActiveDirectoryConfig {

    private static final String BINARY_ATTRIBUTES_KEY = "java.naming.ldap.attributes.binary";
    private static final String TIMEOUT_KEY = "com.sun.jndi.ldap.connect.timeout";

    private final ActiveDirectoryProperties activeDirectoryProperties;
    private final LdapQueryFactory ldapQueryFactory;
    private final LdapMapperFactory ldapMapperFactory;

    public ActiveDirectoryConfig(ActiveDirectoryProperties activeDirectoryProperties,
                                 LdapQueryFactory ldapQueryFactory,
                                 LdapMapperFactory ldapMapperFactory) {
        this.activeDirectoryProperties = activeDirectoryProperties;
        this.ldapQueryFactory = ldapQueryFactory;
        this.ldapMapperFactory = ldapMapperFactory;
    }

    /**
     * @see this#getLdapContextSource(ActiveDirectoryProperties).
     */
    @Bean
    public LdapContextSource ldapContextSource() {
        return getLdapContextSource(activeDirectoryProperties);
    }

    /**
     * Обертка над коннектором к LDAP.
     *
     * @return {@link LdapTemplate}
     */
    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(ldapContextSource());
    }

    /**
     * Резолвер ролей пользователя из LDAP.
     *
     * @return {@link LdapUserDetailsContextMapper}
     */
    @Bean
    public UserDetailsContextMapper userDetailsContextMapper() {
        return new LdapUserDetailsContextMapper(ldapTemplate(), ldapQueryFactory, ldapMapperFactory);
    }

    /**
     * Конфигурация Kerberos сервлет-фильра.
     *
     * @param authenticationManager {@link AuthenticationManager}
     * @return {@link SpnegoAuthenticationProcessingFilter}
     */
    @Bean
    public SpnegoAuthenticationProcessingFilter spnegoAuthenticationProcessingFilter(
        AuthenticationManager authenticationManager) {

        SpnegoAuthenticationProcessingFilter filter = new SpnegoAuthenticationProcessingFilter();
        filter.setAuthenticationManager(authenticationManager);
        //TODO implement handlers
        //filter.setSuccessHandler(new KerberosSuccessHandler(nameBase));
        //filter.setFailureHandler(new KerberosFailureHandler());
        return filter;
    }

    /**
     * Конфигурация Kerberos провайдера аутентификации.
     *
     * @return {@link KerberosServiceAuthenticationProvider}
     */
    @Bean
    public KerberosServiceAuthenticationProvider kerberosServiceAuthenticationProvider() throws Exception {
        KerberosServiceAuthenticationProvider provider = new KerberosServiceAuthenticationProvider();
        provider.setTicketValidator(sunJaasKerberosTicketValidator());
        provider.setUserDetailsService(ldapUserDetailsService());
        return provider;
    }

    /**
     * Конфигурация валидатора Kerberos тикетов.
     *
     * @return {@link SunJaasKerberosTicketValidator}
     */
    @Bean
    public SunJaasKerberosTicketValidator sunJaasKerberosTicketValidator() throws Exception {
        SunJaasKerberosTicketValidator ticketValidator = new SunJaasKerberosTicketValidator();

        ticketValidator.setServicePrincipal(
            activeDirectoryProperties.getKerberos().getApplicationAccount()
        );
        ticketValidator.setKeyTabLocation(
            new FileSystemResource(activeDirectoryProperties.getKerberos().getKeytabLocation())
        );
        ticketValidator.setDebug(
            activeDirectoryProperties.getKerberos().isDebugEnabled()
        );
        ticketValidator.afterPropertiesSet();

        return ticketValidator;
    }

    /**
     * Конфигурация подключения к LDAP c использованием протокола Kerberos.
     *
     * @return {@link KerberosLdapContextSource}
     */
    @Bean
    public KerberosLdapContextSource kerberosLdapContextSource() throws Exception {
        KerberosLdapContextSource contextSource = new KerberosLdapContextSource(
            activeDirectoryProperties.getKerberos().getKrbHostUrl()
        );
        contextSource.setLoginConfig(loginConfig());
        return contextSource;
    }

    /**
     * Конфигурация LDAP {@link UserDetailsService}.
     *
     * @return {@link LdapUserDetailsService}
     */
    @Bean
    public LdapUserDetailsService ldapUserDetailsService() {
        FilterBasedLdapUserSearch userSearch = new FilterBasedLdapUserSearch(
            activeDirectoryProperties.getKerberos().getSearchBase(),
            activeDirectoryProperties.getKerberos().getSearchFilter(),
            ldapContextSource()
        );
        LdapUserDetailsService service = new LdapUserDetailsService(userSearch);
        service.setUserDetailsMapper(userDetailsContextMapper());
        return service;
    }

    /**
     * Конфигурация авторизации приложения на Kerberos-сервере.
     *
     * @return {@link SunJaasKrb5LoginConfig}
     */
    @Bean
    public SunJaasKrb5LoginConfig loginConfig() throws Exception {
        SunJaasKrb5LoginConfig loginConfig = new SunJaasKrb5LoginConfig();

        loginConfig.setKeyTabLocation(
            new FileSystemResource(activeDirectoryProperties.getKerberos().getKeytabLocation())
        );
        loginConfig.setServicePrincipal(
            activeDirectoryProperties.getKerberos().getApplicationAccount()
        );
        loginConfig.setDebug(
            activeDirectoryProperties.getKerberos().isDebugEnabled()
        );
        loginConfig.setIsInitiator(
            activeDirectoryProperties.getKerberos().isInitiator()
        );
        loginConfig.afterPropertiesSet();

        return loginConfig;
    }

    /**
     * Конфигурация подключения к LDAP.
     *
     * @return {@link LdapContextSource}
     */
    private LdapContextSource getLdapContextSource(ActiveDirectoryProperties ldapProperties) {
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl(ldapProperties.getUrl());
        if (ldapProperties.isEnabled()) {
            configureLdapContext(ldapContextSource, ldapProperties);
        }
        return ldapContextSource;
    }

    /**
     * Конфигурация подключения к LDAP.
     */
    private void configureLdapContext(LdapContextSource ldapContextSource,
                                      ActiveDirectoryProperties ldapProperties) {

        Map<String, Object> baseEnvironmentProperties = new HashMap<>();
        baseEnvironmentProperties.put(BINARY_ATTRIBUTES_KEY, ldapProperties.getAttributes().getBinaryAttributes());
        baseEnvironmentProperties.put(TIMEOUT_KEY, ldapProperties.getTimeout());

        ldapContextSource.setBase(ldapProperties.getBase());
        ldapContextSource.setUserDn(ldapProperties.getUser().getDn());
        ldapContextSource.setPassword(ldapProperties.getUser().getPassword());
        ldapContextSource.setReferral(ldapProperties.getReferralMode());
        ldapContextSource.afterPropertiesSet();
        ldapContextSource.setBaseEnvironmentProperties(baseEnvironmentProperties);
    }
}
