package com.raheemspence.dto;

import lombok.Data;

/*
    DTO = data transfer object -- an object used to carry data between layers (usually frontend --> backend)

    We use it so frontend doesnt directly control our User entity. They are used to control what data the frontend is
    allowed to send into the backend
 */
@Data
public class LoginRequest {

    private String email;
    private String password;

}
