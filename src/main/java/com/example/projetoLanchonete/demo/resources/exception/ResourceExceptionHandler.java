package com.example.projetoLanchonete.demo.resources.exception;

import com.example.projetoLanchonete.demo.service.exception.ObjectAlreadyExistsException;
import com.example.projetoLanchonete.demo.service.exception.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Not Found", e.getMessage(),request.getRequestURI() );
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ObjectAlreadyExistsException.class)
    public ResponseEntity<StandardError> objectAlreadyExists(ObjectAlreadyExistsException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Conflict", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}
