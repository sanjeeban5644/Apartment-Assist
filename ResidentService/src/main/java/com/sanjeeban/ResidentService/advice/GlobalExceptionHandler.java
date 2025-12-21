package com.sanjeeban.ResidentService.advice;


import com.sanjeeban.ResidentService.customException.RequestLimitReached;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RequestLimitReached.class)
    public ResponseEntity<Map<String,Object>> handleRequestLimitReachedException(RequestLimitReached ex){
        Map<String,Object> body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.NOT_FOUND.value(),
                "error", "Complaint limit reached for today. Please try tomorrow",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

}

