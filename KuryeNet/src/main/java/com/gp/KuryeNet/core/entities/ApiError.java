package com.gp.KuryeNet.core.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@Schema(name = "ApiError", description = "Standard error payload for failed requests.")
public class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Schema(description = "Error timestamp.", example = "25-02-2026 10:15:30")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status for the error.", example = "BAD_REQUEST")
    private HttpStatus status;

    @Schema(description = "Human-readable error message.", example = "invalid input(s)")
    private String message;

    @Schema(description = "Field-level validation errors.", example = "{\"email\":\"must be a well-formed email address\"}")
    private Map<?, ?> errors;

    @Schema(description = "Additional error details.")
    private Set<?> details;

    public ApiError(Map<?, ?> errors) {
        this.timestamp = LocalDateTime.now();
        this.status = HttpStatus.BAD_REQUEST;
        this.message = null;
        this.errors = errors;
        this.details = null;
    }

    public ApiError(String message, Map<?, ?> errors, Set<?> details) {
        this.timestamp = LocalDateTime.now();
        this.status = HttpStatus.BAD_REQUEST;
        this.message = message;
        this.errors = errors;
        this.details = details;
    }

    public ApiError(HttpStatus httpStatus, String message, Map<?, ?> errors, Set<?> details) {
        this.timestamp = LocalDateTime.now();
        this.status = httpStatus;
        this.message = message;
        this.errors = errors;
        this.details = details;
    }

}
