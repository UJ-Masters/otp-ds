package za.ac.uj.otp.exception;

import java.util.Date;

public class ErrorResponse {
    private Date timeStamp;
    private int status;
    private String message;

    public ErrorResponse(Date timeStamp, int status, String message) {
        this.timeStamp = timeStamp;
        this.status = status;
        this.message = message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
