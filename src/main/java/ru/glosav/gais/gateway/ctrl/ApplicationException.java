package ru.glosav.gais.gateway.ctrl;

public class ApplicationException extends Exception {
    private static final long serialVersionUID = 1L;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }
    public ApplicationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
    public ApplicationException() {
        super();
    }
}