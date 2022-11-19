package com.example.demo.student.errors;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorControllerAdvice {
    // Custom Exception on exception package
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorResponse> handleBadRequestException(Exception e){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        // Converting the stack trace to String
        String stackTrace = this.convertStackTraceToString(e);
        return buildResponseEntity(status, e.getMessage(), stackTrace);
    }
    // Custom Exception for Students
    @ExceptionHandler(StudentNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleStudentNotFoundException(Exception e){
        HttpStatus status = HttpStatus.NOT_FOUND;
        // Converting the stack trace to String
        String stackTrace = this.convertStackTraceToString(e);
        return buildResponseEntity(status, e.getMessage(), stackTrace);
    }

    /**
     *
     * @param e
     * @return ResponseEntity
     * Custom Exception handling for Spring boot Validation added on the Entity with @NotNull, @Email annotations
     * and @Valid on the Controllers request params
     * Convert the Default Exception message to custom by getting the Field errors from the Binding Result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<String> customFieldErrors = fieldErrors.stream().map(fieldError ->
            "Validation Error: " + fieldError.getRejectedValue() + ' ' + fieldError.getDefaultMessage()).toList();

        String stackTrace = this.convertStackTraceToString(e);
        return buildResponseEntity(status, customFieldErrors.toString(), stackTrace);
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
        return buildResponseEntity(status, e.getMessage(), stackTrace);
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
     * @param message
     * @param stackTrace
     * @return Response Entity with error message
     */
    protected ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus status, String message, String stackTrace){
        return new ResponseEntity<>(
                new ErrorResponse(status, message, stackTrace),
                status
        );
    }

}
