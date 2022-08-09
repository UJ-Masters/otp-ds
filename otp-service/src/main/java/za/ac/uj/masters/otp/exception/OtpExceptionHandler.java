package za.ac.uj.masters.otp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class OtpExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(SomethingWentWrongException ex) {
        ErrorResponse error =
                new ErrorResponse(new Date(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        ErrorResponse error =
                new ErrorResponse(new Date(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(OtpExistsException ex) {
        ErrorResponse error =
                new ErrorResponse(new Date(),
                        HttpStatus.CREATED.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CREATED);
    }
}
