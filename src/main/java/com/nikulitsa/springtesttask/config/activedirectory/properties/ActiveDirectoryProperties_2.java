package com.nikulitsa.springtesttask.config.activedirectory.properties;

import com.nikulitsa.springtesttask.config.activedirectory.ActiveDirectoryConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component(ActiveDirectoryConfig.ACTIVE_DIRECTORY_PROPERTIES_PREFIX + 2)
@ConfigurationProperties(prefix = "ad2")
public class ActiveDirectoryProperties_2 extends AbstractActiveDirectoryProperties {
}
