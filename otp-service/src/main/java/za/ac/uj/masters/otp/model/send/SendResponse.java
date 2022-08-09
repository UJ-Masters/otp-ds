package za.ac.uj.masters.otp.model.send;

public class SendResponse {
    private boolean result;
    private String code;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
