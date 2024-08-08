package com.omicroncorp.docuware.dto;

public record TokenEndpoint(
    String access_token,
    int expires_in,
    String token_type,
    String scope) {
}
