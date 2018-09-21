package app.ExceptionHandler.exceptions;

import app.enums.ErrorMessages;
import lombok.Data;

/**
 * Created by Andrey Nazarov on 9/18/2018.
 */

@Data
public class LimitException extends CustomApiException {
    private static final long serialVersionUID = 1L;

    public LimitException() {
        super(ErrorMessages.LIMIT.getMessage(), ErrorMessages.LIMIT.getCode());
    }

    public LimitException(String errorMessage, String code) {
        super(errorMessage, code);
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
