package za.ac.uj.masters.otp.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp")
public class Otp {
    @Id
    @Column(name = "contact")
    private String deliveryAddress;

    @Column(name = "code")
    private String code;

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;

    public Otp() {
    }

    public Otp(String deliveryAddress, String code, LocalDateTime timeStamp) {
        this.deliveryAddress = deliveryAddress;
        this.code = code;
        this.timeStamp = timeStamp;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
