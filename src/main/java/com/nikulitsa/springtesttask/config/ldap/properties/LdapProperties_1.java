package com.nikulitsa.springtesttask.config.ldap.properties;

import com.nikulitsa.springtesttask.config.ldap.LdapConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component(LdapConfig.LDAP_PROPERTIES_PREFIX + 1)
@ConfigurationProperties(prefix = "ldap1")
public class LdapProperties_1 extends AbstractLdapProperties {
}
