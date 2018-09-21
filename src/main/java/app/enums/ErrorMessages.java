package app.enums;

/**
 * Created by Andrey Nazarov on 9/18/2018.
 */
public enum ErrorMessages {
    ID("The id must be a valid positive integer.", "Invalid id"),
    OFFSET("The offset must be a valid non-negative integer.", "Invalid offset"),
    LIMIT("Limit value should be numeric value in range [1, 10].", "Invalid limit"),
    RESOURCE_NOT_FOUND("The requested resource was not found.", "Resource not found");

    private String message;
    private String code;

    ErrorMessages(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
