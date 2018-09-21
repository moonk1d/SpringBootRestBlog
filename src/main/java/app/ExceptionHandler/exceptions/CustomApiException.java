package app.ExceptionHandler.exceptions;

import lombok.Data;

/**
 * Created by Andrey Nazarov on 9/18/2018.
 */
@Data
public class CustomApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    protected String errorMessage;
    protected String code;

    public CustomApiException() {
        super();
    }

    public CustomApiException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public CustomApiException(String errorMessage, String code) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.code = code;
    }
}
