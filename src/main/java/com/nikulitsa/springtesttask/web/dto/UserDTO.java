package com.nikulitsa.springtesttask.web.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nikulitsa.springtesttask.entities.AppUser;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserDTO {

    private Long id;

    private String login;

    private String email;

    public UserDTO(){
    }

    public UserDTO(AppUser appUser) {
        this.id = appUser.getId();
        this.login = appUser.getUsername();
        this.email = appUser.getMail();
    }
}
