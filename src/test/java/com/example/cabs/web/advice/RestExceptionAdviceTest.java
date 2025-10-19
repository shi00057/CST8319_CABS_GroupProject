package com.example.cabs.web.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class RestExceptionAdviceTest {
    @Test
    void conflictOnIntegrityViolation() {
        RestExceptionAdvice handler = new RestExceptionAdvice();
        HttpServletRequest req = new MockHttpServletRequest("POST","/patient/book");
        SQLException sql = new SQLException("dup","23000",1062);
        DataIntegrityViolationException ex = new DataIntegrityViolationException("dup", sql);
        ResponseEntity<ApiError> resp = handler.handleDataAccess(ex, req);
        assertEquals(HttpStatus.CONFLICT, resp.getStatusCode());
        assertEquals(409, resp.getBody().status());
        assertEquals("/patient/book", resp.getBody().path());
    }

    @Test
    void internalServerOnAny() {
        RestExceptionAdvice handler = new RestExceptionAdvice();
        HttpServletRequest req = new MockHttpServletRequest("GET","/x");
        ResponseEntity<ApiError> resp = handler.handleAny(new RuntimeException("x"), req);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
        assertEquals(500, resp.getBody().status());
    }
}
