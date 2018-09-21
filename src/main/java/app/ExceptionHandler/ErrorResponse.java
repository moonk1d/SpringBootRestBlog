package app.ExceptionHandler;

import lombok.Data;

/**
 * Created by Andrey Nazarov on 9/18/2018.
 */
@Data
public class ErrorResponse {
    private String code;
    private String message;
}
