package com.nikulitsa.springtesttask;

import com.nikulitsa.springtesttask.config.ldap.properties.LdapProperties_1;
import com.nikulitsa.springtesttask.config.ldap.properties.LdapProperties_2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {LdapProperties_1.class, LdapProperties_2.class})
public class SpringTestTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTestTaskApplication.class, args);
    }
}
