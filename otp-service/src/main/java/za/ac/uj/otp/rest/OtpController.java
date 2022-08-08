package za.ac.uj.otp.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.uj.otp.model.send.SendRequest;
import za.ac.uj.otp.model.send.SendResponse;
import za.ac.uj.otp.model.validate.ValidateRequest;
import za.ac.uj.otp.model.validate.ValidateResponse;
import za.ac.uj.otp.service.OtpService;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin({"*"})
public class OtpController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OtpService otpService;

    @Autowired
    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }


    @PostMapping("/send")
    @ResponseBody
    public SendResponse sendOtp(@RequestBody SendRequest request) {
        return otpService.sendOtp(request);
    }

    @PostMapping("/validate")
    @ResponseBody
    public ValidateResponse validateOtp(HttpServletRequest servletRequest, @RequestBody ValidateRequest request) {
        boolean override = Boolean.parseBoolean(servletRequest.getHeader("Override"));
        return otpService.validateOtp(request, override);
    }

}
