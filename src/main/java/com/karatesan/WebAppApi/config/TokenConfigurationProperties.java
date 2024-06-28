package com.karatesan.WebAppApi.config;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "com.karatesan.web-app-api.token")
//mapuje zmienen stworzeone w application.yml do odpowiednich miejsc
//now, when you load your application configuration from application.yml,
//Spring Boot automatically binds the properties under the specified prefix to the corresponding fields
//in your TokenConfigurationProperties class. So, the private and
//public keys specified under com.karatesan.web-app-api.token.access-token.private-key
//and com.karatesan.web-app-api.token.access-token.public-key respectively will be mapped to the privateKey
//and publicKey fields in your AccessToken inner class.
public class TokenConfigurationProperties {

    @Valid
    private AccessToken accessToken = new AccessToken();

    @Valid
    private RefreshToken refreshToken = new RefreshToken();

    @Valid
    private VerificationToken verificationToken = new VerificationToken();


    @Getter
    @Setter
    public class AccessToken{

        @NotBlank
        private String privateKey;

        @NotBlank
        private String publicKey;

        @NotNull
        @Positive
        private Integer validity;

    }

    @Getter
    @Setter
    public class RefreshToken{

        @NotNull
        @Positive
        private Integer validity;
    }

    @Getter
    @Setter
    public class VerificationToken {
        @NotNull
        @Positive
        private Integer validity;
    }
}
