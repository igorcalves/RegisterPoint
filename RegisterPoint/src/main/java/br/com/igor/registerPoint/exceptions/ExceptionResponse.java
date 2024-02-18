package br.com.igor.registerPoint.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {

    private LocalDateTime timeStamp;

	private String message;

	private String details;
    
}
