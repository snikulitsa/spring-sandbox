package com.nikulitsa.springsandbox.config.userdetails;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Расширенный {@link UserDetails}.
 *
 * @author Sergey Nikulitsa
 */
public interface CustomUserDetails extends UserDetails {

    UserType getUserType();

    CustomUserDetails setUserType(UserType userType);
}
