package com.example.cabs.web.advice;

import java.time.OffsetDateTime;

public record ApiError(int status, String error, String message, String path, OffsetDateTime timestamp) {}
