package com.nikulitsa.springsandbox.config.security;

import com.nikulitsa.springsandbox.config.activedirectory.properties.ActiveDirectoryProperties;
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

/**
 * Настройки безопасности приложения.
 *
 * @author Sergey Nikulitsa
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final KerberosServiceAuthenticationProvider kerberosServiceAuthenticationProvider;
    private final SpnegoAuthenticationProcessingFilter spnegoAuthenticationProcessingFilter;
    private final LdapContextSource ldapContextSource;
    private final UserDetailsContextMapper userDetailsContextMapper;
    private final ActiveDirectoryProperties activeDirectoryProperties;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService,
                                 KerberosServiceAuthenticationProvider kerberosServiceAuthenticationProvider,
                                 @Lazy SpnegoAuthenticationProcessingFilter spnegoAuthenticationProcessingFilter,
                                 LdapContextSource ldapContextSource,
                                 UserDetailsContextMapper userDetailsContextMapper,
                                 ActiveDirectoryProperties activeDirectoryProperties) {
        this.userDetailsService = userDetailsService;
        this.kerberosServiceAuthenticationProvider = kerberosServiceAuthenticationProvider;
        this.spnegoAuthenticationProcessingFilter = spnegoAuthenticationProcessingFilter;
        this.ldapContextSource = ldapContextSource;
        this.userDetailsContextMapper = userDetailsContextMapper;
        this.activeDirectoryProperties = activeDirectoryProperties;
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

        if (activeDirectoryProperties.isEnabled() && activeDirectoryProperties.getKerberos().isEnabled()) {
            http
                .exceptionHandling()
                .authenticationEntryPoint(new SpnegoEntryPoint())
                .and()
                .addFilterBefore(spnegoAuthenticationProcessingFilter, BasicAuthenticationFilter.class);
        }

        http

            .csrf().disable()
            .formLogin()
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
                "/api/ldap/**",
                "/api/blockDiagram/**",
                "/files/**"
            ).permitAll()
            .anyRequest().authenticated();


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());

        if (activeDirectoryProperties.isEnabled()) {
            auth
                .ldapAuthentication()
                .contextSource(ldapContextSource)
                .userSearchFilter(activeDirectoryProperties.getUser().getSearchFilter())
                .userDetailsContextMapper(userDetailsContextMapper);

            if (activeDirectoryProperties.getKerberos().isEnabled()) {
                auth
                    .authenticationProvider(kerberosServiceAuthenticationProvider);
            }
        }
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
