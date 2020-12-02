package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String userName;
    private String userPassword;
    private String userEmail;
    private Boolean isBlocked;

}
