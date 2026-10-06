package com.uniminuto.clinica.exception;

import com.uniminuto.clinica.exception.dto.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja la excepción de negocio propia del proyecto.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException ex) {

        ErrorResponse error = new ErrorResponse(
                ex.getStatus().value(),
                "BAD REQUEST",
                ex.getMessage()
        );

        return ResponseEntity
                .status(ex.getStatus())
                .body(error);
    }

    /**
     * Maneja la excepción de validación usada por la capa de servicios.
     * Sin este handler las validaciones de negocio terminaban como HTTP 500
     * porque solo existia un handler genérico para Exception.
     */
    @ExceptionHandler(org.apache.coyote.BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleCoyoteBadRequest(
            org.apache.coyote.BadRequestException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "BAD REQUEST",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    /**
     * Maneja los parámetros obligatorios faltantes en la URL.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameter(
            MissingServletRequestParameterException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "BAD REQUEST",
                "El parámetro obligatorio '" + ex.getParameterName() + "' no fue enviado"
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    /**
     * Maneja los cuerpos JSON mal formados o con tipos incompatibles.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadableMessage(
            HttpMessageNotReadableException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "BAD REQUEST",
                "El cuerpo de la solicitud no es válido"
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    /**
     * Maneja las violaciones de integridad de la base de datos
     * (llaves foráneas, valores duplicados, campos obligatorios).
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "BAD REQUEST",
                "La operación viola una restricción de la base de datos"
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    /**
     * Maneja cualquier otra excepción no contemplada.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL SERVER_ERROR",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}