package com.nikulitsa.springtesttask.config.kerberos;

import com.nikulitsa.springtesttask.config.ldap.LdapConfig;
import com.nikulitsa.springtesttask.config.ldap.properties.AbstractLdapProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.ldap.core.AuthenticationSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.core.support.SimpleDirContextAuthenticationStrategy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.kerberos.authentication.KerberosServiceAuthenticationProvider;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosTicketValidator;
import org.springframework.security.kerberos.client.config.SunJaasKrb5LoginConfig;
import org.springframework.security.kerberos.client.ldap.KerberosLdapContextSource;
import org.springframework.security.kerberos.web.authentication.SpnegoAuthenticationProcessingFilter;
import org.springframework.security.kerberos.web.authentication.SpnegoEntryPoint;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.LdapUserDetailsService;

import java.util.Hashtable;

@Configuration
public class KerberosConfig {

    private final AbstractLdapProperties ldapProperties;
    private final LdapContextSource ldapContextSource;
    private String keytabLocation = "/home/s_nikulitsa/opt/app.keytab";
    private String appAccount = "HTTP/app.mydomain.org@MYDOMAIN.ORG";
    private String ldapUrl = "ldap://WIN-8DC93AUDVBU.mydomain.org/";
    private String searchFilter = "(| (userPrincipalName={0}) (sAMAccountName={0}))";
//    private String appAccount = "HTTP/app.mydomain.org";

    public KerberosConfig(LdapConfig ldapConfig) {
        this.ldapContextSource = ldapConfig.getLdapContextSourceFromContext(1);
        this.ldapProperties = ldapConfig.getLdapPropertiesFromContext(1);
    }

//    @Bean
//    public SpnegoEntryPoint spnegoEntryPoint() {
//        return new SpnegoEntryPoint("/login");
//    }

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
        //
        provider.setUserDetailsService(ldapUserDetailsService());
        return provider;
    }

    @Bean
    public SunJaasKerberosTicketValidator sunJaasKerberosTicketValidator() {
        SunJaasKerberosTicketValidator ticketValidator = new SunJaasKerberosTicketValidator();
        //
        ticketValidator.setServicePrincipal(appAccount);
        //
        ticketValidator.setKeyTabLocation(new FileSystemResource(keytabLocation));
        ticketValidator.setDebug(true);
        return ticketValidator;
    }

    @Bean
    public KerberosLdapContextSource kerberosLdapContextSource() throws Exception {
//        KerberosLdapContextSource contextSource = new KerberosLdapContextSource(ldapProperties.getUrl());
        KerberosLdapContextSource contextSource = new KerberosLdapContextSource(ldapUrl);
        contextSource.setLoginConfig(loginConfig());

//        contextSource.setAuthenticationSource(new LdapAdminCredentials(
//            ldapProperties.getUser().getDn(),
//            ldapProperties.getUser().getPassword()
//        ));

//        contextSource.setUserDn(ldapProperties.getUser().getDn());
//        contextSource.setPassword(ldapProperties.getUser().getPassword());

//        SimpleDirContextAuthenticationStrategy simpleDirContextAuthenticationStrategy
//            = new SimpleDirContextAuthenticationStrategy();
//        simpleDirContextAuthenticationStrategy.setupEnvironment(
//            new Hashtable<>(),
//            ldapProperties.getUser().getDn(),
//            ldapProperties.getUser().getPassword()
//        );
//        contextSource.setAuthenticationStrategy(simpleDirContextAuthenticationStrategy);

        return contextSource;
    }

    //TODO create custom LdapUserDetailsService
    @Bean
    public LdapUserDetailsService ldapUserDetailsService() throws Exception {
        FilterBasedLdapUserSearch userSearch = new FilterBasedLdapUserSearch(
            "",
            searchFilter,
//            kerberosLdapContextSource()
            ldapContextSource
        );
        LdapUserDetailsService service = new LdapUserDetailsService(userSearch);
        service.setUserDetailsMapper(new LdapUserDetailsMapper());
        return service;
    }

    public SunJaasKrb5LoginConfig loginConfig() throws Exception {
        SunJaasKrb5LoginConfig loginConfig = new SunJaasKrb5LoginConfig();
        //
        loginConfig.setKeyTabLocation(new FileSystemResource(keytabLocation));
        //
        loginConfig.setServicePrincipal(appAccount);
        loginConfig.setDebug(true);
        loginConfig.setIsInitiator(true);
//        loginConfig.setUseTicketCache(false);
        loginConfig.afterPropertiesSet();
        return loginConfig;
    }

    private static class LdapAdminCredentials implements AuthenticationSource {

        private final String dn;

        private final String password;

        public LdapAdminCredentials(String dn, String password) {
            this.dn = dn;
            this.password = password;
        }

        @Override
        public String getPrincipal() {
            return dn;
        }

        @Override
        public String getCredentials() {
            return password;
        }
    }
}
