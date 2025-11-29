package com.sanjeeban.AdminService.customException;



public class ResidentNotFound extends RuntimeException{


    public ResidentNotFound(String msg){
        super(msg);
    }
}
