package com.apiregions.apiregions.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class Message {
    public static ResponseEntity<Object> ErrorResponse(String message, HttpStatus status, Object object) {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("message", message);
        map.put("status", status.value());
        map.put("data", object);

        return new ResponseEntity<Object>(map, status);
    }
}
