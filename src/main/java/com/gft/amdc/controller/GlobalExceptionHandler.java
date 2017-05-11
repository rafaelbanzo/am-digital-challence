package com.gft.amdc.controller;

import com.gft.amdc.domain.error.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo handleException(HttpServletRequest req, Exception e) {
        logger.log(Level.SEVERE, e.getMessage(), e);
        return new ErrorInfo(req.getRequestURI(), req.getMethod(), e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo handleException(HttpServletRequest req, MethodArgumentNotValidException e) {
        return new ErrorInfo(req.getRequestURI(), req.getMethod(), e);
    }

}
