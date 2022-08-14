package za.ac.uj.masters.otp.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.uj.masters.otp.model.send.SendRequest;
import za.ac.uj.masters.otp.model.send.SendResponse;
import za.ac.uj.masters.otp.model.validate.ValidateRequest;
import za.ac.uj.masters.otp.model.validate.ValidateResponse;
import za.ac.uj.masters.otp.service.OtpService;
import za.ac.uj.masters.otp.service.OtpServiceV2;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;

@RestController("/v1.0")
@CrossOrigin({"*"})
public class OtpController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OtpService otpService;
    private final OtpServiceV2 otpServiceV2;

    @Autowired
    public OtpController(final OtpService otpService,
                         final OtpServiceV2 otpServiceV2) {
        this.otpService = otpService;
        this.otpServiceV2 = otpServiceV2;
    }

    @PostMapping("/v1/send")
    @ResponseBody
    public SendResponse sendOtp(@RequestBody SendRequest request) {
        return otpService.sendOtp(request);
    }

    @PostMapping("/v1/validate")
    @ResponseBody
    public ValidateResponse validateOtp(final HttpServletRequest servletRequest,
                                        @RequestBody ValidateRequest request) {
        boolean override = Boolean.parseBoolean(servletRequest.getHeader("Override"));
        logger.info("isOverride on ? = {}", override);
        return otpService.validateOtp(request, override);
    }

    @PostMapping("/v2/send")
    @ResponseBody
    public SendResponse sendOtpV2(@RequestBody SendRequest request) throws URISyntaxException {
        return otpServiceV2.sendOtp(request);
    }

    @PostMapping("/v2/validate")
    @ResponseBody
    public ValidateResponse validateOtpV2(final HttpServletRequest servletRequest,
                                          @RequestBody ValidateRequest request) {
        boolean override = Boolean.parseBoolean(servletRequest.getHeader("Override"));
        logger.info("isOverride on ? = {}", override);
        return otpServiceV2.validateOtp(request, override);
    }

}
