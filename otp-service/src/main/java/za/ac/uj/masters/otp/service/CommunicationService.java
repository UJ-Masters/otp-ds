package za.ac.uj.masters.otp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import za.ac.uj.masters.otp.exception.SomethingWentWrongException;
import za.ac.uj.masters.otp.mapper.ResponseMapper;
import za.ac.uj.masters.otp.model.send.SendRequest;
import za.ac.uj.masters.otp.model.send.SendResponse;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Component
public class CommunicationService {

    private final Logger log = LoggerFactory.getLogger(CommunicationService.class);

    private static final String Authorization = "Authorization";

    @Value("${communication.base.url}")
    private String baseUrl;

    private final RestTemplate restTemplate;
    private final HttpServletRequest httpServletRequest;
    private final ResponseMapper responseMapper;

    @Autowired
    public CommunicationService(final RestTemplate restTemplate,
                                final HttpServletRequest httpServletRequest,
                                final ResponseMapper responseMapper) {
        this.restTemplate = restTemplate;
        this.httpServletRequest = httpServletRequest;
        this.responseMapper = responseMapper;
    }

    public SendResponse sendEmail(SendRequest sendRequest, String otp) throws URISyntaxException {

        log.info("calling sendEmail");

        HttpEntity<SendRequest> request = getRequest(sendRequest, getHeaders());

        URI uri = new URI(String.format("%s/email", baseUrl));

        ResponseEntity<SendResponse> result = getResponse(uri, request);

        if (result.hasBody()) {
            return responseMapper.map(Objects.requireNonNull(result.getBody()).isResult(), otp);
        }
        throw new SomethingWentWrongException("Failed to call Communication Service");
    }

    public SendResponse sendSms(SendRequest sendRequest, String otp) throws URISyntaxException {

        log.info("calling sendSms");

        HttpEntity<SendRequest> request = getRequest(sendRequest, getHeaders());

        URI uri = new URI(String.format("%s/sms", baseUrl));

        ResponseEntity<SendResponse> result = getResponse(uri, request);

        if (result.hasBody()) {
            return responseMapper.map(Objects.requireNonNull(result.getBody()).isResult(), otp);
        }

        throw new SomethingWentWrongException("Failed to call Communication Service");
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set(Authorization, httpServletRequest.getHeader(Authorization));

        return headers;
    }

    private HttpEntity<SendRequest> getRequest(SendRequest sendRequest, HttpHeaders headers) {
        return new HttpEntity<>(sendRequest, headers);
    }

    private ResponseEntity<SendResponse> getResponse(URI uri, HttpEntity<SendRequest> request) {
        return restTemplate.postForEntity(uri, request, SendResponse.class);
    }

}
