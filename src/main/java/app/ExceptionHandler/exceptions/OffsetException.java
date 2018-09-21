package app.ExceptionHandler.exceptions;

import app.enums.ErrorMessages;
import lombok.Data;

/**
 * Created by Andrey Nazarov on 9/18/2018.
 */
@Data
public class OffsetException extends CustomApiException {
    private static final long serialVersionUID = 1L;

    public OffsetException() {
        super(ErrorMessages.OFFSET.getMessage(), ErrorMessages.OFFSET.getCode());
    }

    public OffsetException(String errorMessage, String code) {
        super(errorMessage, code);
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
