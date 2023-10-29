package com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel;


import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String password;
}
