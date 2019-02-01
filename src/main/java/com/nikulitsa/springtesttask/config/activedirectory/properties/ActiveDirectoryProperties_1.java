package com.nikulitsa.springtesttask.config.activedirectory.properties;

import com.nikulitsa.springtesttask.config.activedirectory.ActiveDirectoryConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component(ActiveDirectoryConfig.ACTIVE_DIRECTORY_PROPERTIES_PREFIX + 1)
@ConfigurationProperties(prefix = "ad1")
public class ActiveDirectoryProperties_1 extends AbstractActiveDirectoryProperties {
}
