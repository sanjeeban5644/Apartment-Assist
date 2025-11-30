package com.sanjeeban.AdminService.customException;

public class ManagerInvalidException extends RuntimeException{
    public ManagerInvalidException(String msg){
        super(msg);
    }
}
