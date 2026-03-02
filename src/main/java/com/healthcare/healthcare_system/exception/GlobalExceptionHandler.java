package com.healthcare.healthcare_system.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🧠 Check if request is coming from Swagger
    private boolean isSwaggerRequest(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui");
    }

    // 🔍 404 - Resource not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        if (isSwaggerRequest(request)) throw ex; // 🚫 don’t touch Swagger
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // ⛔ 403 - Business unauthorized
    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<Object> handleUnauthorized(
            UnauthorizedActionException ex,
            HttpServletRequest request
    ) {
        if (isSwaggerRequest(request)) throw ex;
        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    // ⚠️ 400 - Invalid appointment status
    @ExceptionHandler(InvalidAppointmentStatusException.class)
    public ResponseEntity<Object> handleInvalidStatus(
            InvalidAppointmentStatusException ex,
            HttpServletRequest request
    ) {
        if (isSwaggerRequest(request)) throw ex;
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 🔒 403 - Spring Security access denied
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest request
    ) {
        if (isSwaggerRequest(request)) throw ex;
        return buildResponse(HttpStatus.FORBIDDEN, "Access is denied");
    }

    // 🔑 401 - Authentication failed
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthentication(
            AuthenticationException ex,
            HttpServletRequest request
    ) {
        if (isSwaggerRequest(request)) throw ex;
        return buildResponse(HttpStatus.UNAUTHORIZED, "Authentication failed");
    }

    // 💥 500 - Fallback (unknown errors)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) throws Exception {

        // 🚨 Let Swagger/OpenAPI exceptions pass
        if (isSwaggerRequest(request)) {
            throw ex;
        }

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Something went wrong"
        );
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "status", 400,
                        "error", "Bad Request",
                        "message", ex.getMessage()
                ));
    }

    // 🛠️ Common response builder (single source of truth)
    private ResponseEntity<Object> buildResponse(
            HttpStatus status,
            String message
    ) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now()); // ⏰ when
        body.put("status", status.value());         // 🔢 status code
        body.put("error", status.getReasonPhrase()); // ❗ error type
        body.put("message", message);               // 📝 message
        return new ResponseEntity<>(body, status);
    }
}
