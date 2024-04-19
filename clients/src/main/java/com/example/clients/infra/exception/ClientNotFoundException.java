package com.example.clients.infra.exception;

public class ClientNotFoundException extends Exception {

    public ClientNotFoundException(String message){
        super(message);
    }
}
