package br.com.igor.registerPoint.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler  {


    @ExceptionHandler(UserAlreadyExistsException.class)
    public final ResponseEntity<ExceptionResponse> handlerAlredyUserExcpetion(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse = 
        new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handlerUserNotFoundExcpetion(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse = 
        new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse,HttpStatus.NOT_FOUND);
    }
    
}
