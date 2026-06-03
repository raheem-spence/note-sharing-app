package com.raheemspence.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    private String email;
    private String username;
    private String password;
    private String confirmPassword;
}
