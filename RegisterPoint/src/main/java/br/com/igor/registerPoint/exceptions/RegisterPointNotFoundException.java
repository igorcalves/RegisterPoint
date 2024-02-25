package br.com.igor.registerPoint.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RegisterPointNotFoundException extends RuntimeException {

    private String message;
    
    public RegisterPointNotFoundException(String msg)
    {
        super(msg);
        this.message = msg;
    }
    
}
