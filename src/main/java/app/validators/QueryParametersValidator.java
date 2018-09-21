package app.validators;

import app.ExceptionHandler.exceptions.IdException;
import app.ExceptionHandler.exceptions.LimitException;
import app.ExceptionHandler.exceptions.OffsetException;

/**
 * Created by Andrey Nazarov on 9/18/2018.
 */
public class QueryParametersValidator {

    public static void validateLimitQueryParameter(String limitValue) throws LimitException {
        int limit;

        try {
            limit = Integer.valueOf(limitValue);
        } catch (NumberFormatException e) {
            throw new LimitException();
        }

        if (limit < 1 || limit > 10) {
            throw new LimitException();
        }
    }

    public static void validateOffsetQueryParameter(String offsetValue) throws OffsetException {
        int offset;

        try {
            offset = Integer.valueOf(offsetValue);
        } catch (NumberFormatException e) {
            throw new OffsetException();
        }

        if (offset < 0) {
            throw new OffsetException();
        }
    }

    public static void validateIdQueryParameter(String idValue) throws IdException {
        long id;

        try {
            id = Long.valueOf(idValue);
        } catch (NumberFormatException e) {
            throw new IdException();
        }

        if (id < 1) {
            throw new IdException();
        }
    }
}
