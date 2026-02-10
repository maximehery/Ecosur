package com.ecosur.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  // 404 - Ressource non trouvée
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
  }

  // 400 - Erreur métier (mauvaise requête, règles non respectées, etc.)
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<String> handleBusinessException(BusinessException ex) {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ex.getMessage());
  }

  // 500 - Autres erreurs inattendues
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGenericException(Exception ex) {
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Erreur interne du serveur");
  }
}
