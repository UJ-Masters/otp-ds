package za.ac.uj.masters.otp.exception;

public class OtpExistsException extends RuntimeException{
    public OtpExistsException(String message) {
        super(message);
    }
}
