package com.qb.security.exception;

public class AlreadyExistEmailException extends RuntimeException{
    public AlreadyExistEmailException(String msg){
        super(msg);
    }
}
