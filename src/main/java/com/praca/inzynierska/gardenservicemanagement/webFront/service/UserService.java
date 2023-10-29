package com.praca.inzynierska.gardenservicemanagement.webFront.service;

import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.LoginRequest;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.LoginResponse;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.RegisterRequest;

public interface UserService {
    LoginResponse login(LoginRequest user);
    void create(RegisterRequest user);
}
