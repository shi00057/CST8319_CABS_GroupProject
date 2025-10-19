package com.example.cabs.web.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice(basePackages = "com.example.cabs.web.controller")
public class RestExceptionAdvice {
    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> handleBadRequest(Exception ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<ApiError> handleNotFound(Exception ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    @ExceptionHandler({CannotCreateTransactionException.class})
    public ResponseEntity<ApiError> handleDbUnavailable(Exception ex, HttpServletRequest req) {
        return build(HttpStatus.SERVICE_UNAVAILABLE, "database unavailable", req);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiError> handleDataAccess(DataAccessException ex, HttpServletRequest req) {
        SQLException sql = rootSql(ex);
        if (sql != null) {
            String state = sql.getSQLState();
            if (state != null) {
                if (state.startsWith("23")) return build(HttpStatus.CONFLICT, msg(ex), req);
                if (state.equals("40001")) return build(HttpStatus.CONFLICT, "transaction conflict", req);
                if (state.equals("45000")) {
                    String m = msg(ex).toLowerCase();
                    if (m.contains("conflict") || m.contains("exists") || m.contains("duplicate")) return build(HttpStatus.CONFLICT, msg(ex), req);
                    return build(HttpStatus.BAD_REQUEST, msg(ex), req);
                }
            }
        }
        return build(HttpStatus.BAD_REQUEST, msg(ex), req);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAny(Exception ex, HttpServletRequest req) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, msg(ex), req);
    }

    private ResponseEntity<ApiError> build(HttpStatus status, String message, HttpServletRequest req) {
        ApiError body = new ApiError(status.value(), status.getReasonPhrase(), message, req.getRequestURI(), OffsetDateTime.now());
        return ResponseEntity.status(status).body(body);
    }

    private SQLException rootSql(Throwable t) {
        Throwable x = t;
        while (x != null) {
            if (x instanceof SQLException s) return s;
            x = x.getCause();
        }
        return null;
    }

    private String msg(Throwable t) {
        String m = t.getMessage();
        return m == null ? t.getClass().getSimpleName() : m;
    }
}
