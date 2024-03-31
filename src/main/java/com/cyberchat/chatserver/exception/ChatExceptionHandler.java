package com.cyberchat.chatserver.exception;

import com.cyberchat.chatserver.model.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ChatExceptionHandler {

    @ExceptionHandler(ChatRoomNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleChatRoomNotFoundException(ChatRoomNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        log.error("Error occured :: ",ex);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("An error occurred. Please try again later.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleMessageNotFoundException(MessageNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public Map<String, String> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String fieldName = ex.getName();
        String errorMessage = "Invalid value for parameter '" + fieldName + "'. Expected a numeric value.";
        return Collections.singletonMap(fieldName, errorMessage);
    }
}