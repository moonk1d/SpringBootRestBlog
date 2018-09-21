package app.ExceptionHandler.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created by Andrey Nazarov on 9/18/2018.
 */
@Data
public class FieldIsRequiredException extends CustomApiException {
    private static final long serialVersionUID = 1L;

    public FieldIsRequiredException(String errorMessage) {
        super(errorMessage, HttpStatus.BAD_REQUEST.getReasonPhrase());
    }
}
