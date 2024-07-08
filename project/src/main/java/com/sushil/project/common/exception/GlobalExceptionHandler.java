package com.sushil.project.common.exception;

import com.sushil.project.auth.exception.LoginFailedException;
import com.sushil.project.common.dto.ResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ResponseDto> handleLoginFailedException(LoginFailedException ex, WebRequest request) {
        ResponseDto response = new ResponseDto( false, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseDto> handleUserNotFoundException(UsernameNotFoundException ex) {
        ResponseDto response = new ResponseDto(false, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ResponseDto> handleValidationException(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                        HttpStatus status,
                                                                        WebRequest webRequest){
        StringBuilder sb = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
           // errors.put(fieldName, message);
            String str = fieldName + ": " + message + "\t";
            sb.append(str);
        });

        String newStr = sb.toString();
        ResponseDto response = new ResponseDto(false, newStr);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
//        ResponseDto response = new ResponseDto(false, ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }


    // Other exception handlers can be added here
}