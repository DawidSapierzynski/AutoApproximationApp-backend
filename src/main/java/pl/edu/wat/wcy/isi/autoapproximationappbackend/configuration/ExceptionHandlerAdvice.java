package pl.edu.wat.wcy.isi.autoapproximationappbackend.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.message.response.ResponseMessage;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.exception.ForbiddenException;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.exception.LoginException;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.exception.MessageException;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.exception.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleException(ResourceNotFoundException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseMessage(e.getMessage()));
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ResponseMessage> handleException(LoginException e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMessage(e.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ResponseMessage> handleException(ForbiddenException e) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ResponseMessage(e.getMessage()));
    }

    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ResponseMessage> handleException(MessageException e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMessage(e.getMessage()));
    }
}
