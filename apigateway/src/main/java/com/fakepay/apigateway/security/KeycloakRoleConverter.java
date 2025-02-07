package com.fakepay.apigateway.security;

import com.nimbusds.jose.shaded.gson.Gson;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    /**
     * Converts a Jwt to a collection of GrantedAuthority objects.
     *
     * @param source A Jwt object containing the user's roles.
     * @return A collection of GrantedAuthority objects representing the user's roles.
     */
    public Collection<GrantedAuthority> convert(Jwt source) {
        Gson gson = new Gson();
        Map<String, Object> realmAccess = gson.fromJson(source.getClaims().get("realm_access").toString(), Map.class);

        if (realmAccess == null || realmAccess.isEmpty()) {
            return Collections.emptyList();
        }

        String rolesString = realmAccess.get("roles").toString();
        List<String> roles = Arrays.asList(rolesString.replaceAll("\\[|\\]", "").split(", "));

        return roles.stream()
                .map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
