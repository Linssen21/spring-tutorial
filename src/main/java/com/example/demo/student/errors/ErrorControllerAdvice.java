package com.example.demo.student.errors;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class ErrorControllerAdvice {
    // Custom Exception on exception package
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorResponse> handleBadRequestException(Exception e){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        // Converting the stack trace to String
        String stackTrace = this.convertStackTraceToString(e);
        return buildResponseEntity(status, e, stackTrace);
    }
    // Custom Exception for Students
    @ExceptionHandler(StudentNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleStudentNotFoundException(Exception e){
        HttpStatus status = HttpStatus.NOT_FOUND;
        // Converting the stack trace to String
        String stackTrace = this.convertStackTraceToString(e);
        return buildResponseEntity(status, e, stackTrace);
    }

    /**
     * @param e
     * @return
     * Fallback Method for Exception Handling
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptions(Exception e){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        // Converting the stack trace to String
        String stackTrace = this.convertStackTraceToString(e);
        return buildResponseEntity(status, e, stackTrace);
    }

    /**
     * @param e
     * @return Stacktrace converted to String
     */
    protected String convertStackTraceToString(Exception e){
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    /**
     *
     * @param status
     * @param e
     * @param stackTrace
     * @return Response Entity with error message
     */
    protected ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus status, Exception e, String stackTrace){
        return new ResponseEntity<>(
                new ErrorResponse(status, e.getMessage(), stackTrace),
                status
        );
    }

}
