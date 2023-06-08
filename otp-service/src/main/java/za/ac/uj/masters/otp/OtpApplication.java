package za.ac.uj.masters.otp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OtpApplication {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(OtpApplication.class, args);
    }

    @Value("${spring.security.oauth2.resource-server.jwt.issuer-uri}")
    private String jwtIssuerUri;

    @Value("${spring.security.oauth2.resource-server.jwt.jwt-set-uri}")
    private String jwtSetUri;

    @Value("${communication.base.url}")
    private String communicationBaseUrl;

    @Bean
    ApplicationListener<ApplicationReadyEvent> onApplicationReadyEventListener(ServerProperties serverProperties) {
        logger.info("issuer uri: {}", jwtIssuerUri);
        logger.info("jwt set uri: {}", jwtSetUri);
        logger.info("communication base url: {}", communicationBaseUrl);

        return evt ->
                logger.info("OTP-DS started: http://localhost:{}{}/swagger-ui.html to use otp-ds",
                        serverProperties.getPort(), serverProperties.getServlet().getContextPath());
    }
}
