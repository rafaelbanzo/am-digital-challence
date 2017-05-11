package com.gft.amdc.controller;

import com.gft.amdc.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class GlocalEceptionHandler {

    private static Logger logger = Logger.getLogger(GlocalEceptionHandler.class);

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception e) {
        logger.log(Level.SEVERE, e.getMessage(), e);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleException(BadRequestException e) {

    }


}
