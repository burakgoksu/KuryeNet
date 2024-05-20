package com.gp.KuryeNet.core.utulities.exception.handlers;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandlers extends ResponseEntityExceptionHandler{
	
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();
        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), Utils.getViolationMsg(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        ApiError apiError = new ApiError(Msg.INVALID.getCustom("%s input(s)"), errors, null);
        return ResponseEntity.badRequest().body(new ErrorDataResult<>( apiError,Msg.FAILED.get()));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        ex.printStackTrace();
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
    	ex.printStackTrace();
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put(ex.getClass().toString(),ex.getSuppressed().toString());
        Set<String> details = new HashSet<String>();
        details.add("Check your credential information");
        ApiError apiError = new ApiError(ex.getMessage(),errors,details);
        return ResponseEntity.badRequest().body(new ErrorDataResult<>(apiError,"Login " + Msg.FAILED.get()));
    }

    @ExceptionHandler({EntityNotExistsException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotExistsException ex, WebRequest request) {
        ex.printStackTrace();
        return ResponseEntity.badRequest().body(new ErrorResult(ex.getMessage()));
    }

    @ExceptionHandler({UKViolationException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(UKViolationException ex, WebRequest request) {
        ex.printStackTrace();
        return ResponseEntity.badRequest().body(new ErrorResult(ex.getMessage()));
    }

    @ExceptionHandler({SameValueException.class})
    public ResponseEntity<Object> handleSameValueException(SameValueException ex, WebRequest request) {
        ex.printStackTrace();
        return ResponseEntity.badRequest().body(new ErrorResult(ex.getMessage()));
    }
    
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();
        Set<String> details = new LinkedHashSet<>();
        details.add(ex.getMessage());
        ApiError apiError = new ApiError(Msg.MALFORMED_JSON_REQUEST.get(), null, details);
        return ResponseEntity.badRequest().body(new ErrorDataResult<>(apiError,Msg.FAILED.get()));
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        ex.printStackTrace();
        Set<String> details = new LinkedHashSet<>();
        details.add(Objects.requireNonNull(ex.getMostSpecificCause()).getMessage());
        ApiError apiError = new ApiError(ex.getCause().getMessage(), null, details);
        return ResponseEntity.badRequest().body(new ErrorDataResult<>(apiError,Msg.FAILED.get()));
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccess(EmptyResultDataAccessException ex, WebRequest request) {
        ex.printStackTrace();
        ApiError apiError = new ApiError(ex.getMostSpecificCause().getMessage(), null, null);
        return ResponseEntity.badRequest().body(new ErrorDataResult<>(apiError,Msg.FAILED.get()));
    }

    @ExceptionHandler({FileSizeLimitExceededException.class})
    public ResponseEntity<Object> handleFileSizeLimitExceededException(FileSizeLimitExceededException ex, WebRequest request) {
        ex.printStackTrace();
        String mb = String.valueOf(Math.round(ex.getPermittedSize() / 1000000.0));
        String msg = String.format("%s. You can upload files up to %s MB", Msg.FILE_TOO_LARGE.get(), mb);
        return ResponseEntity.badRequest().body(new ErrorResult(msg));
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMostSpecificCause().getMessage(), null, null);
        return ResponseEntity.badRequest().body(new ErrorDataResult<>(apiError,Msg.FAILED.get()));
    }


}
