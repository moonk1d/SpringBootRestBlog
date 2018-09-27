package app.controllers.REST;

import app.ExceptionHandler.ErrorResponse;
import app.ExceptionHandler.exceptions.*;
import app.validators.QueryParametersValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */
@RestController
public class MainController {

    public Pageable pageRequestBuilder(String offset, String limit) {
        int defaultLimit = 5;
        int defaultOffset = 0;

        Pageable page = new PageRequest(defaultOffset, defaultLimit);

        if (limit != null) {
            QueryParametersValidator.validateLimitQueryParameter(limit);
        }

        if (offset != null) {
            QueryParametersValidator.validateOffsetQueryParameter(offset);
        }

        if (limit != null && offset != null) {
            page = new PageRequest(Integer.valueOf(offset), Integer.valueOf(limit));
        } else if (limit != null && offset == null) {
            page = new PageRequest(defaultOffset, Integer.valueOf(limit));
        } else if (offset != null && limit == null) {
            page = new PageRequest(Integer.valueOf(offset), defaultLimit);
        }

        return page;
    }

    @ExceptionHandler({LimitException.class,
            OffsetException.class,
            IdException.class,
            ResourceNotFoundException.class,
            FieldIsRequiredException.class})
    public ResponseEntity<ErrorResponse> exceptionHandler(CustomApiException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(ex.getCode());
        error.setMessage(ex.getMessage());

        if (ex instanceof ResourceNotFoundException) {
            return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<ErrorResponse> exceptionHandler(MethodArgumentNotValidException ex) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(new Date());
        errorDetails.setCode("Validation Failed");

        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        String field = ex.getBindingResult().getFieldError().getField();

        errorDetails.setMessage(String.format("'%s' field %s", field, message));

        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
