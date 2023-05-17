package com.qb.security.exception;

public class AlreadyExistEmailException extends Exception{
    public AlreadyExistEmailException(String msg){
        super(msg);
    }
}
