package com.isj.gestionmateriel.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
// import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SessionFilter implements Filter {
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;
    @Value("${keycloak.introspection.url}")
    private String introspectionUrl;

    private String extractAccessTokenFromRequest(ServletRequest request) {
        System.out.println("Extracting token...");
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String authorizationHeader = httpRequest.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                return authorizationHeader.substring(7); // Remove "Bearer " to get the token
            }
        }
        return null; // Return null if no valid token is found
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
                System.out.println("SessionFilter is called");
        // TODO Auto-generated method stub
        try {
            System.out.println("Now in try!");
            // Step 1: Extract the access token from the request
            String accessToken = extractAccessTokenFromRequest(request);
            // System.out.println(accessToken.substring(0, 10) + "...");

            // Step 2: Create an HTTP request to Keycloak's introspection endpoint
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(clientId, clientSecret);
            // System.out.println("header: " + headers.toString());

            // Set up the request body with the token
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("token", accessToken);
            requestBody.add("client_id", clientId);
            // System.out.println("body: " + requestBody.toString());

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);
            // System.err.println("entity: " + entity.toString());
            // Make the HTTP request
            // System.out.println("introspectionUrl: " + introspectionUrl);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(introspectionUrl, entity, String.class);
            // System.out.println("responseEntity: " + responseEntity.toString());

            // Step 3: Handle the introspection response
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                System.out.println("Check token success!");
                ObjectMapper objectMapper = new ObjectMapper();
                String introspectionResponse = responseEntity.getBody();
                Map<String, Object> responseMap = objectMapper.readValue(introspectionResponse, new TypeReference<Map<String, Object>>() {});
                boolean isActive = (boolean) responseMap.get("active");
                System.out.println("isActive: " + isActive);
                if(isActive) {
                    System.out.println("Token is active!");
                    
                } else {
                    System.out.println("Token is inactive!");
                    System.out.println("Response with 401");
                    HttpServletResponse httpResponse = (HttpServletResponse) response;
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                // System.out.println(responseEntity.getBody());
            } else {
                System.out.println("Check token failed!");
                // Token is invalid, deny access or take appropriate action
                // System.out.println(responseEntity.getBody());
            }
        } catch (Exception ex) {
            System.out.println("Now in catch!");
            System.out.println(ex.getMessage());
            System.out.println();
            // Handle exceptions as needed
        }
        chain.doFilter(request, response);
        System.out.println("----- END OF FILTER -----");
        // throw new UnsupportedOperationException("Unimplemented method 'doFilter'");
    }
}
