package com.example.urlshortener.controller;


import com.example.urlshortener.dto.ExceptionResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@ControllerAdvice(basePackageClasses = UrlShortenerController.class)
public class RestExceptionHandler {

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<ExceptionResponseDto> handleResponseStatusException(ResponseStatusException e, WebRequest request) {
        ExceptionResponseDto response = new ExceptionResponseDto();
        log.info("Expected exception {}", e.getMessage());
        response.setStatus(e.getStatus());
        response.setReason(e.getReason());
        return new ResponseEntity<>(response, e.getStatus());
    }
}
