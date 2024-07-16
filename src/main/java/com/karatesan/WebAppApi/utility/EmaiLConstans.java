package com.karatesan.WebAppApi.utility;

public class EmaiLConstans {

    public static final String ACCOUNT_VERIFICATION_SUBJECT = "Verify your account";
    public static final String ACCOUNT_VERIFICATION_MESSAGE = "Hello ${name}!<p> Click this link to confirm your email address and complete setup for your account: </p><a href=${link}> ACTIVATE </a> <p>The link will expire after 24 hours.</>";
    public static final String ACTIVATION_ENDPOINT = "http://localhost:8080/api/activate/";
}
