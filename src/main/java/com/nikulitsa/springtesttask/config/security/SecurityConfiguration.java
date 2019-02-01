package com.nikulitsa.springtesttask.config.security;

import com.nikulitsa.springtesttask.config.activedirectory.ActiveDirectoryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.kerberos.authentication.KerberosServiceAuthenticationProvider;
import org.springframework.security.kerberos.web.authentication.SpnegoAuthenticationProcessingFilter;
import org.springframework.security.kerberos.web.authentication.SpnegoEntryPoint;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final KerberosServiceAuthenticationProvider kerberosServiceAuthenticationProvider;
    private final SpnegoAuthenticationProcessingFilter spnegoAuthenticationProcessingFilter;
    private final ActiveDirectoryConfig activeDirectoryConfig;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService,
                                 KerberosServiceAuthenticationProvider kerberosServiceAuthenticationProvider,
                                 @Lazy SpnegoAuthenticationProcessingFilter spnegoAuthenticationProcessingFilter,
                                 ActiveDirectoryConfig activeDirectoryConfig) {
        this.userDetailsService = userDetailsService;
        this.kerberosServiceAuthenticationProvider = kerberosServiceAuthenticationProvider;
        this.spnegoAuthenticationProcessingFilter = spnegoAuthenticationProcessingFilter;
        this.activeDirectoryConfig = activeDirectoryConfig;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
        return new AjaxAuthenticationSuccessHandler();
    }

    @Bean
    public AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
        return new AjaxAuthenticationFailureHandler();
    }

    @Bean
    public AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler() {
        return new AjaxLogoutSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (activeDirectoryConfig.isKerberosEnabled()) {
            http
                .exceptionHandling()
                .authenticationEntryPoint(new SpnegoEntryPoint())
                .and()
                .addFilterBefore(spnegoAuthenticationProcessingFilter, BasicAuthenticationFilter.class);
        }

        http

            .csrf().disable()
            .formLogin()
//            .loginPage("/login")
            .loginProcessingUrl("/login")
            .successHandler(ajaxAuthenticationSuccessHandler())
            .failureHandler(ajaxAuthenticationFailureHandler())
            .usernameParameter("username")
            .passwordParameter("password")
            .permitAll()

            .and()

            .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler(ajaxLogoutSuccessHandler())
            .permitAll()

            .and()

            .headers()
            .frameOptions()
            .disable()

            .and()

            .authorizeRequests()
            .antMatchers(
                "/api/registration/mail",
                "/ldap_test/**",
                "/files/**"
            ).permitAll()
            .anyRequest().authenticated()
        ;


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        for (int adNumber : ActiveDirectoryConfig.LDAP_CONTEXT_NUMBERS) {
            if (activeDirectoryConfig.isLdapEnabled(adNumber)) {
                LdapContextSource ldapContextSource = activeDirectoryConfig.getLdapContextSourceFromContext(adNumber);

                String userSearchFilter = activeDirectoryConfig.getUserSearchFilter(adNumber);

                UserDetailsContextMapper userDetailsContextMapper = activeDirectoryConfig
                    .getUserDetailsContextMapperFromContext(adNumber);

                auth
                    .ldapAuthentication()
                    .contextSource(ldapContextSource)
                    .userSearchFilter(userSearchFilter)
                    .userDetailsContextMapper(userDetailsContextMapper);
            }
        }

        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());

        if (activeDirectoryConfig.isKerberosEnabled()) {
            auth
                .authenticationProvider(kerberosServiceAuthenticationProvider);
        }
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
