package com.karatesan.WebAppApi.utility;


import com.karatesan.WebAppApi.config.TokenConfigurationProperties;
import com.karatesan.WebAppApi.model.security.BlogUser;
import com.karatesan.WebAppApi.model.security.role.Privilege;
import com.karatesan.WebAppApi.model.security.role.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@EnableConfigurationProperties(TokenConfigurationProperties.class)
public class JwtUtility {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String SCOPE_CLAIM_NAME = "scp";

    private final TokenConfigurationProperties tokenConfigurationProperties;
    private final String issuer;


    public JwtUtility(final TokenConfigurationProperties tokenConfigurationProperties,
                      @Value("${spring.application.name}") final String issuer){
        this.issuer = issuer;
        this.tokenConfigurationProperties = tokenConfigurationProperties;
    }

    //=======================================================================================================


    public String generateAccessToken(@NonNull final BlogUser user){
        final String jti = String.valueOf(UUID.randomUUID());
        final String audience = String.valueOf(user.getId());
        final Integer accessTokenValidity = tokenConfigurationProperties.getAccessToken().getValidity();
        final long expiration = TimeUnit.MINUTES.toMillis(accessTokenValidity);
        final Date currentTimestamp = new Date(System.currentTimeMillis());
        final Date expirationTimestamp = new Date(System.currentTimeMillis() + expiration);

        final var claims = new HashMap<String,String>();
        claims.put(SCOPE_CLAIM_NAME, rolesToSpaceSeparatedString(user.getRoles()));

        return Jwts.builder()
                .claims(claims)
                .expiration(expirationTimestamp)
                .id(jti)
                .issuer(issuer)
                .issuedAt(currentTimestamp)
                .audience().add(audience)
                .and()
                .signWith(getPrivateKey(),Jwts.SIG.RS512)
                .compact();
    }

    public String rolesToSpaceSeparatedString(Collection<Role>roles){

        final String rolesString = roles.stream().map(Role::getName).collect(Collectors.joining(" "));
        final String privileges = roles.stream().flatMap(p -> p.getPrivileges().stream()).map(Privilege::getName).collect(Collectors.joining(" "));
        return String.join(" ",rolesString,privileges);
    }

    //=======================================================================================================

    public Duration getTimeUntilExpiration(@NonNull final String token) {
        final var expirationTimestamp = extractClaim(token, Claims::getExpiration).toInstant();
        final var currentTimestamp = new Date().toInstant();
        return Duration.between(currentTimestamp, expirationTimestamp);
    }

    //=======================================================================================================

    public List<GrantedAuthority> getAuthority(@NonNull final String token){
        String scopes = extractClaim(token, claims -> claims.get(SCOPE_CLAIM_NAME, String.class));
        return Arrays.stream(scopes.split(" "))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    //=======================================================================================================

    public String getJti(@NonNull final String token) {
        return extractClaim(token, Claims::getId);
    }

    //=======================================================================================================

    @SneakyThrows
    private PrivateKey getPrivateKey()  {
        final var privateKey = tokenConfigurationProperties.getAccessToken().getPrivateKey();
        final var sanitizedPrivateKey = sanitizeKey(privateKey);

        final var decodedPrivateKey = Decoders.BASE64.decode(sanitizedPrivateKey);
        final var spec = new PKCS8EncodedKeySpec(decodedPrivateKey);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    //=======================================================================================================


    @SneakyThrows
    private PublicKey getPublicKey() {
        final var publicKey = tokenConfigurationProperties.getAccessToken().getPublicKey();
        final var sanitizedPublicKey = sanitizeKey(publicKey);

        final var decodedPublicKey = Decoders.BASE64.decode(sanitizedPublicKey);
        final var spec = new X509EncodedKeySpec(decodedPublicKey);

        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    //=======================================================================================================


    public Long extractUserId(@NonNull final String token){
        final var audience = extractClaim(token, Claims::getAudience).iterator().next();
        return Long.parseLong(audience);
    }

    //=======================================================================================================

    private <T> T extractClaim(@NonNull final String token,@NonNull final Function<Claims,T> claimsResolver) {
        
        final var sanitizedToken = token.replace(BEARER_PREFIX,"");
        final var claims = Jwts.parser()
                .requireIssuer(issuer)
                .verifyWith(getPublicKey())
                .build()
                .parseSignedClaims(sanitizedToken)
                .getPayload();
        return claimsResolver.apply(claims);
    }

    //=======================================================================================================

    private String sanitizeKey(@NonNull final String key) {
        return key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\n", "")
                .replaceAll("\\s", "");
    }





}
