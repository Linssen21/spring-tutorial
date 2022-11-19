package com.example.demo.student.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * @author Linssen
 * @since 1.0
 * @version 1.0
 * <br>
 * Create a custom Error Response in JSON format that shows the following:
 * code, status, message, stackTrace and Data
 *
 * This Class is used to create the shape of the Error Response
 *
 */

@Getter
@Setter
public class ErrorResponse {
    // Set custom date format for the timestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;
    private int code;
    private String status;
    private String message;
    private String stackTrace;
    private Object data;

    /**
     * No params
     * @implNote ErrorResponse()
     * only timestamp has a value
     */
    public ErrorResponse(){
        timestamp = new Date();
    }

    /**
     * @param httpStatus
     * @param message
     * @implSpec ErrorResponse(HttpStatus.OK, "Error Message") <br>
     * this(); calls the first constructor which returns timestamp
     */
    public ErrorResponse(HttpStatus httpStatus, String message){
        // Calls first constructor
        this();
        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
    }

    /**
     * @param httpStatus
     * @param message
     * @param stackTrace
     * @implSpec ErrorResponse(HttpStatus.OK, "Error Message", "Stack trace string") <br>
     * this(httpStatus, message); calls the second constructor
     */
    public ErrorResponse(HttpStatus httpStatus, String message, String stackTrace){
        // Calls second constructor
        this(httpStatus, message);
        this.stackTrace = stackTrace;
    }

    /**
     * @param httpStatus
     * @param message
     * @param stackTrace
     * @param data
     * @implSpec ErrorResponse(HttpStatus.OK, "Error Message", "Stack trace string", ) <br>
     * this(httpStatus, message); calls the second constructor
     */
    public ErrorResponse(HttpStatus httpStatus, String message, String stackTrace, Object data){
        this(httpStatus, message, stackTrace);
        this.data = data;
    }

}
