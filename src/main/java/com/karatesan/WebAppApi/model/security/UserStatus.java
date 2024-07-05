package com.karatesan.WebAppApi.model.security;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    //TODO principal.activated

    PENDING_APPROVAL("Pending Approval"),
    APPROVED("Approved"),
    DEACTIVATED("Deactivated");

    private final String value;

}
