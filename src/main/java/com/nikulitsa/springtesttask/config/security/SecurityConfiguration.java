package com.nikulitsa.springtesttask.config.security;

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
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final LdapContextSource contextSource;
    private final UserDetailsContextMapper userDetailsContextMapper;
    private final LdapAuthoritiesPopulator ldapAuthoritiesPopulator;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService,
                                 AuthenticationManagerBuilder authenticationManagerBuilder,
                                 LdapContextSource contextSource,
                                 UserDetailsContextMapper userDetailsContextMapper,
                                 LdapAuthoritiesPopulator ldapAuthoritiesPopulator) {
        this.userDetailsService = userDetailsService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.contextSource = contextSource;
        this.userDetailsContextMapper = userDetailsContextMapper;
        this.ldapAuthoritiesPopulator = ldapAuthoritiesPopulator;
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

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/api/**", corsConfiguration);
//        return source;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .cors()
//                .and()
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


        auth
            .ldapAuthentication()
            .contextSource(contextSource)
//            .userSearchFilter("(sAMAccountName={0})")
            .userSearchFilter("(distinguishedName={0})")
//            .ldapAuthoritiesPopulator(ldapAuthoritiesPopulator)
            .userDetailsContextMapper(userDetailsContextMapper);

        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }
}
