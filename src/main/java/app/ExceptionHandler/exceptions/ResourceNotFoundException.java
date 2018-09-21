package app.ExceptionHandler.exceptions;

import app.enums.ErrorMessages;
import lombok.Data;

/**
 * Created by Andrey Nazarov on 9/18/2018.
 */
@Data
public class ResourceNotFoundException extends CustomApiException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException() {
        super(ErrorMessages.RESOURCE_NOT_FOUND.getMessage(), ErrorMessages.RESOURCE_NOT_FOUND.getCode());
    }

    public ResourceNotFoundException(String errorMessage, String code) {
        super(errorMessage, code);
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
