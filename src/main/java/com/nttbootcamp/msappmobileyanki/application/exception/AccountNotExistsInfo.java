package com.nttbootcamp.msappmobileyanki.application.exception;

public class AccountNotExistsInfo extends Exception{
    private static final long serialVersionUID = 1L;

    private String message = "Account doesn't exists, disposing operation";

    public AccountNotExistsInfo() {

    }

    public AccountNotExistsInfo(String optMessage) {
        this.message=optMessage;
    }
    public String getMessage() {
        return message;
    }
}
