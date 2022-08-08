package za.ac.uj.otp.exception;

public class SomethingWentWrongException extends RuntimeException {
    public SomethingWentWrongException(String message) {
        super(message);
    }
}
