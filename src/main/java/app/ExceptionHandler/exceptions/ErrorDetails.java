package app.ExceptionHandler.exceptions;

import lombok.Data;

import java.util.Date;

/**
 * Created by Andrey on 21.09.2018.
 */
@Data
public class ErrorDetails {
    private String code;
    private String message;
    private Date timestamp;
}
