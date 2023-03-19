package za.ac.uj.masters.otp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import za.ac.uj.masters.otp.exception.SomethingWentWrongException;
import za.ac.uj.masters.otp.model.DeliveryType;
import za.ac.uj.masters.otp.model.send.SendRequest;
import za.ac.uj.masters.otp.model.send.SendResponse;
import za.ac.uj.masters.otp.model.validate.ValidateRequest;
import za.ac.uj.masters.otp.model.validate.ValidateResponse;

import java.net.URISyntaxException;
import java.util.Random;

@Component
public class OtpServiceV2 {

    private final Logger log = LoggerFactory.getLogger(OtpServiceV2.class);
    private static final Integer EXPIRE_MINS = 1;
    //private final LoadingCache otpCache;

    private final CommunicationService communicationService;

    public OtpServiceV2(final CommunicationService communicationService){
        super();
        this.communicationService = communicationService;
        /*otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
                .build(new CacheLoader() {
                    @Override
                    public Object load(Object o) {
                        return 0;
                    }
                });*/
    }

    private int generateOTP(String key){
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        /*otpCache.put(key, otp);*/
        return otp;
    }

    private int getOtp(String key){
        try{
            return 1;//otpCache.get(key);
        }catch (Exception e){
            return 0;
        }
    }

    private void clearOTP(String key){
        /*otpCache.invalidate(key);*/
    }

    public SendResponse sendOtp(final SendRequest request) {
        try {
            String deliveryAddress;
            if (request.getDeliveryType() == DeliveryType.E) {
                deliveryAddress = request.getEmail();
            } else {
                deliveryAddress = request.getMobileNumber();
            }
            int otp = generateOTP(deliveryAddress);

            if (request.getDeliveryType() == DeliveryType.E) {
                return communicationService.sendEmail(request, String.valueOf(otp));
            }
            SendResponse response = new SendResponse();
            response.setResult(true);
            return response;
        } catch (URISyntaxException ex) {
            log.error(ex.getMessage());
            throw new SomethingWentWrongException(ex.getMessage());
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            throw new SomethingWentWrongException(ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public ValidateResponse validateOtp(final ValidateRequest request,
                                        final boolean override) {
        ValidateResponse response = new ValidateResponse();
        if(!override){
            //Validate the Otp
            Integer otpNum = Integer.parseInt(request.getOtp());

            String username;
            if(DeliveryType.E == request.getDeliveryType()){
                username = request.getEmail();
            }else{
                username = request.getMobileNumber();
            }

            if (otpNum >= 0) {
                int serverOtp = 1;/*getOtp(username);*/
                if (serverOtp > 0) {
                    if (otpNum == serverOtp) {
                        clearOTP(username);
                        response.setResult(true);
                    } else {
                        response.setResult(false);
                    }
                } else {
                    response.setResult(false);
                }
            }else {
                response.setResult(false);
            }
            return response;
        }else{
            response.setResult(true);
        }
        return response;
    }
}
