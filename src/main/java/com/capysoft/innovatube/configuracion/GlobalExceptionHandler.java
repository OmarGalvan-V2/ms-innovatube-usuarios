package com.capysoft.innovatube.configuracion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.capysoft.innovatube.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> lstErrores = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            lstErrores.add(error.getDefaultMessage())
        );
        
        ErrorResponse errorResponse = new ErrorResponse(
            "Error de validación", 
            lstErrores, 
            HttpStatus.BAD_REQUEST.value()
        );
        
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        List<String> lstErrores = List.of(ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            "Error en el procesamiento", 
            lstErrores, 
            HttpStatus.BAD_REQUEST.value()
        );
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
}