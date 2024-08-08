package com.omicroncorp.docuware.dto;

import java.util.List;

public record OpenIdConfiguration(
    String issuer,
    String jwks_uri,
    String authorization_endpoint,
    String token_endpoint,
    String userinfo_endpoint,
    String end_session_endpoint,
    String check_session_iframe,
    String revocation_endpoint,
    String introspection_endpoint,
    String device_authorization_endpoint,
    String backchannel_authentication_endpoint,
    String pushed_authorization_request_endpoint,
    boolean require_pushed_authorization_requests,
    boolean frontchannel_logout_supported,
    boolean frontchannel_logout_session_supported,
    boolean backchannel_logout_supported,
    boolean backchannel_logout_session_supported,
    List<String> scopes_supported,
    List<String> claims_supported,
    List<String> grant_types_supported,
    List<String> response_types_supported,
    List<String> response_modes_supported,
    List<String> token_endpoint_auth_methods_supported,
    List<String> id_token_signing_alg_values_supported,
    List<String> subject_types_supported,
    List<String> code_challenge_methods_supported,
    boolean request_parameter_supported,
    List<String> request_object_signing_alg_values_supported,
    List<String> prompt_values_supported,
    boolean authorization_response_iss_parameter_supported,
    List<String> backchannel_token_delivery_modes_supported,
    boolean backchannel_user_code_parameter_supported,
    List<String> dpop_signing_alg_values_supported,
    String windows_auth_endpoint) {

}
