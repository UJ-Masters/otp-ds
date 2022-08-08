package za.ac.uj.otp.model.validate;

public class ValidateResponse {
    private boolean result;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return String.format("result => %s }", result);
    }
}
