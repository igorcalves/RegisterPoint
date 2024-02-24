package br.com.igor.registerPoint.domain.dto;

import java.time.LocalDateTime;

import br.com.igor.registerPoint.domain.StatusCheck;

public record RegisterPointDto(LocalDateTime date,StatusCheck status) {
    
}
