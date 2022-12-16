package com.apiregions.apiregions.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PaysNotFoundException extends RuntimeException{
    public PaysNotFoundException( String message)
    {
        super(message);
    }
}
