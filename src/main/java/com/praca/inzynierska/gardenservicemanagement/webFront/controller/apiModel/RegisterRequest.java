package com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel;

import lombok.Data;

@Data
public class RegisterRequest {
    private String userName;
    private String password;
}
