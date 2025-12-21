package com.sanjeeban.ResidentService.customException;

public class RequestLimitReached extends RuntimeException{
    public RequestLimitReached(String msg){
        super(msg);
    }
}
