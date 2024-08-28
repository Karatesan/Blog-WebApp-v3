package com.karatesan.WebAppApi.dto.authentication;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenSuccessResponseDto {

    private String accessToken;
    private String refreshToken;
}
