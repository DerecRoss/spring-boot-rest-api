package com.rest_api.spring_boot_rest_api.exception;

import java.util.Date;

public record ExceptionResponse(Date timeStamp, String message, String details) {}
