package br.com.igor.registerPoint.exceptions;

public class UserNotFoundException extends RuntimeException {

    private String message;
    
    public UserNotFoundException(String msg)
    {
        super(msg);
        this.message = msg;
    }

    
}
