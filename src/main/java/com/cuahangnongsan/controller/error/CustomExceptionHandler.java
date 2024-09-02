//package com.cuahangnongsan.controller.error;
//
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//import java.nio.file.AccessDeniedException;
//
//@ControllerAdvice
//public class CustomExceptionHandler {
//
////    @ExceptionHandler(NoHandlerFoundException.class)
////    public String handle403(NoHandlerFoundException ex) {
////        return "error/403"; // Đảm bảo rằng file 404.html tồn tại
////    }
////    @ExceptionHandler(NoHandlerFoundException.class)
////    public String handle404(NoHandlerFoundException ex) {
////        return "error/404"; // Đảm bảo rằng file 404.html tồn tại
////    }
////
////    @ExceptionHandler(AccessDeniedException.class)
////    public String handle405(AccessDeniedException ex) {
////        return "error/405"; // Đảm bảo rằng file 405.html tồn tại
////    }
//
////    @ExceptionHandler(Exception.class)
////    public String handleException(Exception ex) {
////        return "error/error"; // Đảm bảo rằng file error.html tồn tại
////    }
//}
//
