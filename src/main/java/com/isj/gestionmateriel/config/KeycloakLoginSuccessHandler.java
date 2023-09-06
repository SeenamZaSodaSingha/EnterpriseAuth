package com.isj.gestionmateriel.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class KeycloakLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakLoginSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {


        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        logger.info("********************* Welcome! " + oidcUser.getName() + " Just landed!");
        // logger.info("*********************" + oidcUser.getName() + " Role is: " + oidcUser.getAttribute("role"));

        request.getSession().setAttribute("username", oidcUser.getName());

        super.onAuthenticationSuccess(request, response, authentication);
    }

}
