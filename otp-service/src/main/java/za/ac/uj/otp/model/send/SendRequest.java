package za.ac.uj.otp.model.send;

import za.ac.uj.otp.model.DeliveryType;

public class SendRequest {

    private String email;
    private String mobileNumber;
    private DeliveryType deliveryType;
    private String message;

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
