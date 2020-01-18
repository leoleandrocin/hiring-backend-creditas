package br.com.creditas.hiringbackend.infrastructure

import br.com.creditas.hiringbackend.infrastructure.exception.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFoundExceptionHandler(exception: ResourceNotFoundException) : ResponseEntity<ErrorMessage> {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorMessage(exception.message))
    }

}