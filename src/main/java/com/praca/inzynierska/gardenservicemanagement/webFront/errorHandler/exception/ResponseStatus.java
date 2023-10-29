package com.praca.inzynierska.gardenservicemanagement.webFront.errorHandler.exception;

public enum ResponseStatus {
    USER_NOT_VALID(401),
    USER_NOT_FOUND(403),
    INTERNAL_SERVER_ERROR(500);

    private final int code;

    ResponseStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
