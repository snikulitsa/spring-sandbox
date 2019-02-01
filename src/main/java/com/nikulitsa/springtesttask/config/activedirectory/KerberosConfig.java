package com.nikulitsa.springtesttask.config.activedirectory;

import com.nikulitsa.springtesttask.config.activedirectory.properties.Kerberos;
import com.nikulitsa.springtesttask.services.ldap.LdapMapperFabric;
import com.nikulitsa.springtesttask.services.ldap.LdapQueryFabric;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.kerberos.authentication.KerberosServiceAuthenticationProvider;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosTicketValidator;
import org.springframework.security.kerberos.client.config.SunJaasKrb5LoginConfig;
import org.springframework.security.kerberos.client.ldap.KerberosLdapContextSource;
import org.springframework.security.kerberos.web.authentication.SpnegoAuthenticationProcessingFilter;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.LdapUserDetailsService;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

@Configuration
public class KerberosConfig {

    static final int KERBEROS_AD_CONTEXT = 1;

    private final LdapContextSource ldapContextSource;
    private final UserDetailsContextMapper userDetailsContextMapper;
    private final Kerberos kerberosProperties;

    public KerberosConfig(ActiveDirectoryConfig activeDirectoryConfig,
                          LdapQueryFabric ldapQueryFabric,
                          LdapMapperFabric ldapMapperFabric) {
        this.ldapContextSource = activeDirectoryConfig
            .getLdapContextSourceFromContext(KERBEROS_AD_CONTEXT);

        this.kerberosProperties = activeDirectoryConfig
            .getActiveDirectoryPropertiesFromContext(KERBEROS_AD_CONTEXT)
            .getKerberos();

        this.userDetailsContextMapper = new KerberosUserDetailsContextMapper(
            activeDirectoryConfig.getLdapTemplateFromContext(KERBEROS_AD_CONTEXT),
            ldapQueryFabric,
            ldapMapperFabric
        );
    }

    @Bean
    public SpnegoAuthenticationProcessingFilter spnegoAuthenticationProcessingFilter(
        AuthenticationManager authenticationManager) {

        SpnegoAuthenticationProcessingFilter filter = new SpnegoAuthenticationProcessingFilter();
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    public KerberosServiceAuthenticationProvider kerberosServiceAuthenticationProvider() throws Exception {
        KerberosServiceAuthenticationProvider provider = new KerberosServiceAuthenticationProvider();
        provider.setTicketValidator(sunJaasKerberosTicketValidator());
        provider.setUserDetailsService(ldapUserDetailsService());
        return provider;
    }

    @Bean
    public SunJaasKerberosTicketValidator sunJaasKerberosTicketValidator() throws Exception {
        SunJaasKerberosTicketValidator ticketValidator = new SunJaasKerberosTicketValidator();

        ticketValidator.setServicePrincipal(
            kerberosProperties.getApplication_account()
        );
        ticketValidator.setKeyTabLocation(
            new FileSystemResource(kerberosProperties.getKeytab_location())
        );
        ticketValidator.setDebug(
            kerberosProperties.isDebug_enabled()
        );
        ticketValidator.afterPropertiesSet();

        return ticketValidator;
    }

    @Bean
    public KerberosLdapContextSource kerberosLdapContextSource() throws Exception {
        KerberosLdapContextSource contextSource = new KerberosLdapContextSource(
            kerberosProperties.getKrb_host_url()
        );
        contextSource.setLoginConfig(loginConfig());
        return contextSource;
    }

    @Bean
    public LdapUserDetailsService ldapUserDetailsService() {
        FilterBasedLdapUserSearch userSearch = new FilterBasedLdapUserSearch(
            kerberosProperties.getSearch_base(),
            kerberosProperties.getSearch_filter(),
            ldapContextSource
        );
        LdapUserDetailsService service = new LdapUserDetailsService(userSearch);
        service.setUserDetailsMapper(userDetailsContextMapper);
        return service;
    }

    @Bean
    public SunJaasKrb5LoginConfig loginConfig() throws Exception {
        SunJaasKrb5LoginConfig loginConfig = new SunJaasKrb5LoginConfig();

        loginConfig.setKeyTabLocation(
            new FileSystemResource(kerberosProperties.getKeytab_location())
        );
        loginConfig.setServicePrincipal(
            kerberosProperties.getApplication_account()
        );
        loginConfig.setDebug(
            kerberosProperties.isDebug_enabled()
        );
        loginConfig.setIsInitiator(
            kerberosProperties.isInitiator()
        );
        loginConfig.afterPropertiesSet();

        return loginConfig;
    }
}
