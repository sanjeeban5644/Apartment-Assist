package com.sanjeeban.AdminService.customException;

import com.sanjeeban.AdminService.service.TechnicianService;

public class TechnicianInvalid extends RuntimeException{
    public TechnicianInvalid(String msg){
        super(msg);
    }
}
