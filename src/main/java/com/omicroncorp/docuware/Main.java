//

package com.omicroncorp.docuware;

import com.omicroncorp.docuware.dto.Document;
import com.omicroncorp.docuware.dto.PlatformClient;
import com.omicroncorp.docuware.dto.TokenEndpoint;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    //The endpoint always end with "Docuware/Platform"
    public static final String API_URL = "https://[instance].docuware.cloud/DocuWare/Platform/";
    //How to get the cabinet ID
    //https://[instance].docuware.cloud/DocuWare/Platform/Browser/Platform/FileCabinets
    //https://app.screencast.com/To0gRQ9BNIqaf
    public static final String cabinetID = "[Cabinet ID]";
    public static final String HostId = "[Server Hostname]";

    public static final Boolean DebugOutput = false;

    public static void main(String[] args) throws IOException {
        String user = "[username]", password = "password";

        var documentName = "SAMPLE_DOCUMENT.pdf";
        var documentBytes = Files.readAllBytes(Paths.get(documentName));

        //Get Client Connection
        PlatformClient client = getPlatformClient(API_URL, DebugOutput);

        //Login into the service
        var tokenEndpoint = Login(client, user, password);
        if (tokenEndpoint == null) {
            System.out.println("Error al iniciar sesión en DocuWare ");
            return;
        }
        //System.out.println("DocuWare REST Api version: " + serviceDescription.Version() + " Token:" + serviceDescription.Token());


        //Set metadata and upload document
        //02 CUENTA
        var metadata = """
                {
                    "Fields": [
                        {
                            "FieldName": "DOCUMENT_TYPE",
                            "Item": "[VALOR]",
                            "ItemElementName": "String"
                        },
                        {
                            "FieldName": "COMPANY",
                            "Item": "[VALOR]",
                            "ItemElementName": "String"
                        },
                        {
                            "FieldName": "CUSTOMER_ID",
                            "Item": "[VALOR]",
                            "ItemElementName": "String"
                        },
                        {
                            "FieldName": "CONTACT",
                            "Item": "[VALOR]",
                            "ItemElementName": "String"
                        },
                        {
                            "FieldName": "DOCUMENT_NUMBER",
                            "Item": "[VALOR]",
                            "ItemElementName": "String"
                        },
                        {
                            "FieldName": "DATE",
                            "Item": "2024-07-07",
                            "ItemElementName": "Date"
                        }
                    ]
                }""";

        try {
            documentBytes = Files.readAllBytes(Paths.get(documentName));
        } catch (IOException e) {
            System.out.println("No se encontro el archivo a cargar \"SAMPLE_DOCUMENT.pdf\"");
            return;
        }

        Response<Document> uploadDocument = UploadDocument(client, tokenEndpoint, metadata, documentName, documentBytes);
        if (uploadDocument == null || uploadDocument.body() == null || !uploadDocument.isSuccessful()) {
            try {
                System.out.println("Error al cargar el documento: " + uploadDocument.errorBody().string());
            } catch (IOException e) {
                System.out.println("Error al cargar el documento, no se pudo obtener el mensaje de error..");
            }
        }

        System.out.println("Documento creado: " + uploadDocument.body().Id());

        //Logout from the service.
        client.logoff(tokenEndpoint.access_token());
    }

    private static PlatformClient getPlatformClient(String Api, Boolean debugOutput) {
        var builder = new Retrofit.Builder()
                .baseUrl(Api)
                .addConverterFactory(GsonConverterFactory.create());
        
        if (debugOutput) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
        }

        var retrofit = builder.build();
        return retrofit.create(PlatformClient.class);
    }

    private static TokenEndpoint Login(PlatformClient client, String user, String password) {
        Response<TokenEndpoint> login;
        try {
            var IdentityServiceInfo = client.GetIdentityServiceInfo().execute();
            var oAuthEndpoint = IdentityServiceInfo.body().IdentityServiceUrl() + "/.well-known/openid-configuration";
            var oAuthConfig = client.GetOpenIdConfiguration(oAuthEndpoint).execute();

            login = client
                    .login(
                        oAuthConfig.body().token_endpoint(),
                        user,
                        password,
                        "password",
                        "docuware.platform",
                        "docuware.platform.net.client")
                    .execute();

        } catch (IOException e) {
            return null;
        }
        var serviceDescription = login.body();

        if (!login.isSuccessful() || serviceDescription == null) {
            return null;
        }

        return serviceDescription;
    }

    private static Response<Document> UploadDocument(PlatformClient client, TokenEndpoint tokenEndpoint, String metadata, String documentName, byte[] documentBytes) {
        var jsonMime = MediaType.parse("application/json");
        var documentMetadata = MultipartBody.Part.createFormData("document", "document.json",
                RequestBody.create(jsonMime, metadata.getBytes(StandardCharsets.UTF_8)));

        var documentMime = MediaType.parse("application/octet-stream");
        List<MultipartBody.Part> files = new ArrayList<>();
        files.add(
                MultipartBody.Part.createFormData("file[]", documentName,
                        RequestBody.create(documentMime, documentBytes)
                )
        );

        Response<Document> uploadDocument;
        try {
            uploadDocument = client
                    .uploadDocument(tokenEndpoint.token_type() + " " + tokenEndpoint.access_token(), cabinetID, documentMetadata, files)
                    .execute();
            return uploadDocument;
        } catch (IOException e) {
            return null;
        }
    }

}