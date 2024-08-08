package com.omicroncorp.docuware.dto;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


public interface PlatformClient {
    @Headers("Accept: application/json")
    @GET("Home/IdentityServiceInfo")
    Call<IdentityServiceInfo> GetIdentityServiceInfo();

    //.well-known/openid-configuration
    @Headers("Accept: application/json")
    @GET()
    Call<OpenIdConfiguration> GetOpenIdConfiguration(@Url String Url);

    //connect/token
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST()
    Call<TokenEndpoint> login(
        @Url String Url,
        @Field("UserName") String username,
        @Field("Password") String password,
        @Field("grant_type") String grant_type,
        @Field("scope") String scope,
        @Field("client_id") String clientId
    );

    @Headers("Accept: application/json")
    @GET("Account/Logoff")
    Call<ResponseBody> logoff(
            @Header("Cookie") String token
    );

    @Multipart
    @Headers("Accept: application/json")
    @POST("FileCabinets/{cabinetId}/Documents")
    Call<Document> uploadDocument (
            @Header("Authorization") String token,
            @Path("cabinetId") String cabinetId,
            @Part MultipartBody.Part document,
            @Part List<MultipartBody.Part> files
    );
}
