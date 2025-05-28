package com.fcamara.config;

import com.fcamara.FixYouApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final Logger LOGGER = LogManager.getLogger(KeycloakRoleConverter.class);

    //TODO Verificar por que não está puxando está vindo null, lembrar que não pode pegar via construtor em outra classe que da erro.
    @Value("${client.value}")
    private String clientValue;

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        // Realm roles
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            List<String> roles = (List<String>) realmAccess.get("roles");
            roles.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
        }

        // Client roles
        Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
        LOGGER.info("Client value: [{}]", clientValue);

        if (resourceAccess != null && resourceAccess.containsKey("fixyou-client-ext")) {
            Map<String, Object> client = (Map<String, Object>) resourceAccess.get("fixyou-client-ext");
            if (client.containsKey("roles")) {
                List<String> clientRoles = (List<String>) client.get("roles");
                clientRoles.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
            }
        }

        return grantedAuthorities;
    }
}
