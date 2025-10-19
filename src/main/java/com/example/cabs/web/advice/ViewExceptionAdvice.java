package com.example.cabs.web.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice(annotations = org.springframework.stereotype.Controller.class)
public class ViewExceptionAdvice {
    @ExceptionHandler({Exception.class})
    public String handle(Model model, Exception ex, HttpServletRequest req) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof IllegalArgumentException) status = HttpStatus.BAD_REQUEST;
        if (ex instanceof DataAccessException) {
            SQLException sql = rootSql(ex);
            if (sql != null && sql.getSQLState() != null) {
                String state = sql.getSQLState();
                if (state.startsWith("23") || state.equals("40001")) status = HttpStatus.CONFLICT;
                else if (state.equals("45000")) status = HttpStatus.BAD_REQUEST;
            } else {
                status = HttpStatus.BAD_REQUEST;
            }
        }
        model.addAttribute("status", status.value());
        model.addAttribute("error", status.getReasonPhrase());
        model.addAttribute("message", msg(ex));
        model.addAttribute("path", req.getRequestURI());
        return "error";
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
