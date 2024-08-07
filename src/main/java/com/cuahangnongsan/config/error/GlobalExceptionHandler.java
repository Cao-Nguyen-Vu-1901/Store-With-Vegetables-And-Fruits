//package com.cuahangnongsan.config.error;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(NoHandlerFoundException.class)
//    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
//    public ResponseEntity<String> handleMethodNotAllowed(NoHandlerFoundException ex) {
//        return new ResponseEntity<>("Method Not Allowed - Custom Message", HttpStatus.METHOD_NOT_ALLOWED);
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<String> handleOtherExceptions(Exception ex) {
//        return new ResponseEntity<>("Internal Server Error - Custom Message", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}
//
