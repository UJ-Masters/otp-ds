package za.ac.uj.otp.mapper;

import org.springframework.stereotype.Component;
import za.ac.uj.otp.model.send.SendResponse;

@Component
public class ResponseMapper {

    public SendResponse map(boolean result, String otp) {
        SendResponse response = new SendResponse();
        response.setResult(result);
        response.setCode(otp);
        return response;
    }

}
