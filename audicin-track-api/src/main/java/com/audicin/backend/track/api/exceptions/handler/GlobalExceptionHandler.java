package com.audicin.backend.track.api.security.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.View;

import com.audicin.backend.track.api.exceptions.controller.ResourceNotFoundException;
import com.audicin.backend.track.api.exceptions.controller.BadRequestException;
import com.audicin.backend.track.api.exceptions.controller.UnauthorizedAccessException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentialsException(BadCredentialsException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
        errorDetail.setProperty("description", "Credentials are incorrect");
        return errorDetail;
    }

    @ExceptionHandler(AccountStatusException.class)
    public ProblemDetail handleAccountStatusException(AccountStatusException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
        errorDetail.setProperty("description", "The account is locked");
        return errorDetail;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
        errorDetail.setProperty("description", "You are not authorized to access this resource");
        return errorDetail;
    }

    @ExceptionHandler(SignatureException.class)
    public ProblemDetail handleSignatureException(SignatureException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
        errorDetail.setProperty("description", "JWT signature is invalid");
        return errorDetail;
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ProblemDetail handleExpiredJwtException(ExpiredJwtException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
        errorDetail.setProperty("description", "JWT token has expired");
        return errorDetail;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), exception.getMessage());
        errorDetail.setProperty("description", "Resource not found");
        return errorDetail;
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestException(BadRequestException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), exception.getMessage());
        errorDetail.setProperty("description", "Bad request");
        return errorDetail;
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ProblemDetail handleUnauthorizedAccessException(UnauthorizedAccessException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
        errorDetail.setProperty("description", "You are not authorized to perform this action");
        return errorDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
        errorDetail.setProperty("description", "Internal server error");
        return errorDetail;
    }
}
