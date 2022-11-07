package com.example.dogapi.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    public ResponseEntity<Object> ok(String message, Object data) {
        return new ResponseEntity<>(new BaseResponse(
                HttpStatus.OK.value(), message, data), HttpStatus.OK);
    }

    public ResponseEntity<?> badRequest(Object data) {
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.BAD_REQUEST.value(),
                        "bad request", data), HttpStatus.BAD_REQUEST);
    }
}
