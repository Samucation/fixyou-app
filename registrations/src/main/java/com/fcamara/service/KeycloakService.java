package com.fcamara.service;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;

@Service
public class KeycloakService {

    // TODO: Substituir futuramente por leitura do .env
    private static final String SERVER_URL = "http://192.168.1.17:8080/";
    private static final String REALM = "fixyourealm";
    private static final String CLIENT_ID = "fixyou-client-ext";
    private static final String CLIENT_SECRET = "qIONV6e1V7uCqjO3dwIq7O73fr2hU4z5";
    private static final String USERNAME = "admfixyou";
    private static final String PASSWORD = "123Mudar";

    private Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(SERVER_URL)
                .realm(REALM)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }

    public String createUser(String username, String email) {
        Keycloak keycloak = getInstance();

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(username);
        user.setEmail(email);

        Response response = keycloak.realm(REALM).users().create(user);

        if (response.getStatus() != 201) {
            throw new RuntimeException("Erro ao criar usuário no Keycloak: " + response.getStatus());
        }

        String location = response.getHeaderString("Location");
        return location != null ? location.substring(location.lastIndexOf("/") + 1) : null;
    }
}
