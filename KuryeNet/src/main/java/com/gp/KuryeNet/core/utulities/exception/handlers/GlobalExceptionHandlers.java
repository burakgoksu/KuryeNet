package com.gp.KuryeNet.core.utulities.exception.handlers;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gp.KuryeNet.core.entities.ApiError;
import com.gp.KuryeNet.core.utulities.Util.Msg;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.exception.exceptions.EntityNotExistsException;
import com.gp.KuryeNet.core.utulities.exception.exceptions.SameValueException;
import com.gp.KuryeNet.core.utulities.exception.exceptions.UKViolationException;
import com.gp.KuryeNet.core.utulities.result.ErrorDataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandlers extends ResponseEntityExceptionHandler{

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandlers.class);
	
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.warn("Validation failed", ex);
        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), Utils.getViolationMsg(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        ApiError apiError = new ApiError(Msg.INVALID.getCustom("%s input(s)"), errors, null);
        return ResponseEntity.badRequest().body(new ErrorDataResult<>( apiError,Msg.FAILED.get()));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        logger.warn("Constraint violation", ex);
        Map<String, String> errors = new LinkedHashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String propPath = violation.getPropertyPath().toString();
            String camelCaseProp = propPath.substring(propPath.lastIndexOf('.') + 1);
            errors.put(camelCaseProp, Utils.getViolationMsg(camelCaseProp, violation.getMessage()));
        }
        ApiError apiError = new ApiError(Msg.INVALID.getCustom("%s input(s)"), errors, null);
        return ResponseEntity.badRequest().body(new ErrorDataResult<>(apiError, Msg.FAILED.get()));
    }
    
    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Object> handleUsernameNotFoundException(BadCredentialsException ex) {
    	logger.warn("Bad credentials", ex);
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put(ex.getClass().toString(),ex.getSuppressed().toString());
        Set<String> details = new HashSet<String>();
        details.add("Check your credential information");
        ApiError apiError = new ApiError(ex.getMessage(),errors,details);
        return ResponseEntity.badRequest().body(new ErrorDataResult<>(apiError,"Login " + Msg.FAILED.get()));
    }

    @ExceptionHandler({LockedException.class})
    public ResponseEntity<Object> handleLockedException(LockedException ex) {
    	logger.warn("Account locked", ex);
        ApiError apiError = new ApiError(ex.getMessage(), null, null);
        return ResponseEntity.status(HttpStatus.LOCKED).body(new ErrorDataResult<>(apiError, "Login " + Msg.FAILED.get()));
    }

    @ExceptionHandler({EntityNotExistsException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotExistsException ex, WebRequest request) {
        logger.warn("Entity not exists", ex);
        return ResponseEntity.badRequest().body(new ErrorResult(ex.getMessage()));
    }

    @ExceptionHandler({UKViolationException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(UKViolationException ex, WebRequest request) {
        logger.warn("Unique constraint violation", ex);
        return ResponseEntity.badRequest().body(new ErrorResult(ex.getMessage()));
    }

    @ExceptionHandler({SameValueException.class})
    public ResponseEntity<Object> handleSameValueException(SameValueException ex, WebRequest request) {
        logger.warn("Same value violation", ex);
        return ResponseEntity.badRequest().body(new ErrorResult(ex.getMessage()));
    }
    
	@Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.warn("Malformed JSON request", ex);
        Set<String> details = new LinkedHashSet<>();
        details.add(ex.getMessage());
        ApiError apiError = new ApiError(Msg.MALFORMED_JSON_REQUEST.get(), null, details);
        return ResponseEntity.badRequest().body(new ErrorDataResult<>(apiError,Msg.FAILED.get()));
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        logger.warn("Data integrity violation", ex);
        Set<String> details = new LinkedHashSet<>();
        details.add(Objects.requireNonNull(ex.getMostSpecificCause()).getMessage());
        ApiError apiError = new ApiError(ex.getCause().getMessage(), null, details);
        return ResponseEntity.badRequest().body(new ErrorDataResult<>(apiError,Msg.FAILED.get()));
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccess(EmptyResultDataAccessException ex, WebRequest request) {
        logger.warn("Empty result data access", ex);
        ApiError apiError = new ApiError(ex.getMostSpecificCause().getMessage(), null, null);
        return ResponseEntity.badRequest().body(new ErrorDataResult<>(apiError,Msg.FAILED.get()));
    }

    @ExceptionHandler({FileSizeLimitExceededException.class})
    public ResponseEntity<Object> handleFileSizeLimitExceededException(FileSizeLimitExceededException ex, WebRequest request) {
        logger.warn("File size limit exceeded", ex);
        String mb = String.valueOf(Math.round(ex.getPermittedSize() / 1000000.0));
        String msg = String.format("%s. You can upload files up to %s MB", Msg.FILE_TOO_LARGE.get(), mb);
        return ResponseEntity.badRequest().body(new ErrorResult(msg));
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.warn("Response write error", ex);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMostSpecificCause().getMessage(), null, null);
        return ResponseEntity.badRequest().body(new ErrorDataResult<>(apiError,Msg.FAILED.get()));
    }


}
