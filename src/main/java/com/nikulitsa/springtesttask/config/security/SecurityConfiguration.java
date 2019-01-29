package com.nikulitsa.springtesttask.config.security;

import com.nikulitsa.springtesttask.config.ldap.LdapConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final LdapConfig ldapConfig;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService,
                                 LdapConfig ldapConfig) {
        this.userDetailsService = userDetailsService;
        this.ldapConfig = ldapConfig;
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
        http
            .csrf().disable()
            .formLogin()
            .loginProcessingUrl("/basicLogin")
            .successHandler(ajaxAuthenticationSuccessHandler())
            .failureHandler(ajaxAuthenticationFailureHandler())
            .usernameParameter("username")
            .passwordParameter("password")
            .permitAll()

            .and()

            .logout()
            .logoutUrl("/basicLogout")
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
            .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        for (int ldapContextNumber: LdapConfig.LDAP_CONTEXT_NUMBERS) {
            if (ldapConfig.isLdapEnabled(ldapContextNumber)) {
                LdapContextSource ldapContextSource = ldapConfig.getLdapContextSourceFromContext(ldapContextNumber);

                String userSearchFilter = ldapConfig.getUserSearchFilter(ldapContextNumber);

                UserDetailsContextMapper userDetailsContextMapper = ldapConfig
                    .getUserDetailsContextMapperFromContext(ldapContextNumber);

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
    }
}
