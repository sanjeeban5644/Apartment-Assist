package com.sanjeeban.ResidentService.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object controllerRequestTimingLogs(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        long timeTaken = (endTime-startTime)/60;
        System.out.println("Time Taken By method ["+joinPoint.getSignature().getName()+"] is "+timeTaken+"s");
        return result;
    }


}