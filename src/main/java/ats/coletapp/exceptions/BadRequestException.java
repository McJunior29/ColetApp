package ats.coletapp.exceptions;


public class BadRequestException extends RuntimeException {
    public BadRequestException(String detail) {
        super(detail);
    }
}
