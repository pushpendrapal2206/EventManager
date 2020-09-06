package com.salesforce.eventmanager.api.exception;

import java.beans.ConstructorProperties;

public class GenericError {
    private final String code;
    private final String message;

    public static GenericError.GenericErrorBuilder builder() {
        return new GenericError.GenericErrorBuilder();
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    @ConstructorProperties({"code", "message"})
    public GenericError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static class GenericErrorBuilder {
        private String code;
        private String message;

        GenericErrorBuilder() {
        }

        public GenericError.GenericErrorBuilder code(String code) {
            this.code = code;
            return this;
        }

        public GenericError.GenericErrorBuilder message(String message) {
            this.message = message;
            return this;
        }

        public GenericError build() {
            return new GenericError(this.code, this.message);
        }
    }
}
