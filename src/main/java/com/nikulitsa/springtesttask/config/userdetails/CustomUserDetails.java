package com.nikulitsa.springtesttask.config.userdetails;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {

    UserType getUserType();

    CustomUserDetails setUserType(UserType userType);
}
