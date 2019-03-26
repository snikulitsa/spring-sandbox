package com.nikulitsa.springsandbox;

import com.nikulitsa.springsandbox.config.activedirectory.properties.ActiveDirectoryProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {ActiveDirectoryProperties.class})
public class SpringSandboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSandboxApplication.class, args);
    }
}
