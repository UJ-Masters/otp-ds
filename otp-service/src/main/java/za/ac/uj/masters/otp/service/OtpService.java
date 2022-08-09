package za.ac.uj.masters.otp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import za.ac.uj.masters.otp.entity.Otp;
import za.ac.uj.masters.otp.exception.SomethingWentWrongException;
import za.ac.uj.masters.otp.model.DeliveryType;
import za.ac.uj.masters.otp.model.send.SendRequest;
import za.ac.uj.masters.otp.model.send.SendResponse;
import za.ac.uj.masters.otp.model.validate.ValidateRequest;
import za.ac.uj.masters.otp.model.validate.ValidateResponse;
import za.ac.uj.masters.otp.repo.OtpRepository;
import za.ac.uj.masters.otp.util.AESUtil;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Component
public class OtpService {

    private final Logger log = LoggerFactory.getLogger(OtpService.class);

    private final CommunicationService communicationService;
    private final OtpRepository otpRepository;

    // Using random method
    private Random randomMethod = new Random();

    @Value("${otp.secret.key}")
    private String secret;

    @Autowired
    public OtpService(final CommunicationService communicationService,
                      final OtpRepository otpRepository) {
        this.communicationService = communicationService;
        this.otpRepository = otpRepository;
    }

    private char[] generateOtp() {

        int size = 4;

        // Using numeric values
        String numbers = "0123456789";

        char[] otp = new char[size];

        for (int i = 0; i < size; i++) {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            otp[i] = numbers
                    .charAt(randomMethod
                            .nextInt(numbers.length()));
        }
        return otp;
    }

    public SendResponse sendOtp(final SendRequest request) {
        try {
            String otp = String.valueOf(generateOtp());
            log.info("otp: {}", otp);
            String eOtp = AESUtil.encrypt(otp, secret);
            request.setMessage(otp);
            if (request.getDeliveryType() == DeliveryType.E) {
                checkOtp(request.getEmail(), eOtp);
                return communicationService.sendEmail(request, otp);
            }
            checkOtp(request.getMobileNumber(), eOtp);

            return communicationService.sendSms(request, otp);

        } catch (URISyntaxException | RestClientException ex) {
            log.error(ex.getMessage());
            throw new SomethingWentWrongException(ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public ValidateResponse validateOtp(final ValidateRequest request, boolean override) {
        log.info("validating otp {}", request.getOtp());
        ValidateResponse response = new ValidateResponse();

        Optional<Otp> result;

        if(!override){
            if (request.getDeliveryType() == DeliveryType.E) {
                result = otpRepository.findById(request.getEmail());
                if(result.isPresent()){
                    otpRepository.deleteById(request.getEmail());
                }
            } else {
                result = otpRepository.findById(request.getMobileNumber());
                if(result.isPresent()){
                    otpRepository.deleteById(request.getMobileNumber());
                }
            }
            response.setResult(map(result, request));
        }else{
            response.setResult(true);
        }

        log.info(response.toString());
        return response;
    }

    private boolean map(final Optional<Otp> result, final ValidateRequest request) {
        if (result.isPresent()) {
            String dOtp = AESUtil.decrypt(result.get().getCode(), secret);

            if (request.getOtp().equals(dOtp)) {
                return true;
            }
        }
        return false;
    }

    private void checkOtp(String deliveryAddress, String eOtp) {
        Optional<Otp> result = otpRepository.findById(deliveryAddress);
        if (result.isPresent()) {
            otpRepository.deleteById(deliveryAddress);
        } else {
            otpRepository.save(new Otp(deliveryAddress, eOtp, LocalDateTime.now()));
        }
    }

}
