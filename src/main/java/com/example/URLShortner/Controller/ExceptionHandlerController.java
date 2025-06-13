package com.example.URLShortner.Controller;

import com.example.URLShortner.Domain.ErrorResponse;
import com.example.URLShortner.Exception.APIException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.slf4j.LoggerFactory;


@ControllerAdvice
public class ExceptionHandlerController {

    Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(value = APIException.class)
    public ResponseEntity<Object> exception(APIException e){
        logger.info(e.toString());
        ErrorResponse error = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);

    }

}
