package com.sample.wishlistDemo.utils;


import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class RestClient {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(RestClient.class);

    private static final String CLIENT_NAME = "caashiring.test";
    private static final String CLIENT_ID = "R64UWNPnzFwkg74XAYiVc6mWpB0Qtu1R";
    private static final String CLIENT_SECRET = "EEmhYGZXGXPkjBpH";
    private static final String SCOPE_VIEW = "hybris.document_view";
    private static final String SCOPE_MANAGE = "hybris.document_manage";
    private static final String OAUTH2_TOKEN_API = "https://api.beta.yaas.io/hybris/oauth2/v1/token";
    private static final String TENNAT = "caashiring";
    private static final String DOCUMENT_SERVICE = "https://api.beta.yaas.io/hybris/document/v1";
    private static final String server = "https://api.beta.yaas.io/hybris/oauth2/v1/";
    private static final String PREWLS = "prewishlists";
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;

    public RestClient() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
    }

    private String token(String scope) {
        HttpHeaders tokenHeader = new HttpHeaders();
        tokenHeader.add("Content-Type", "application/x-www-form-urlencoded");
        tokenHeader.add("Accept", "*/*");
        String body = String.format("client_id=%s&client_secret=%s&grant_type=client_credentials&scope=%s",
                CLIENT_ID, CLIENT_SECRET, scope);
        LOG.debug(body);
        HttpEntity<String> requestEntity = new HttpEntity<>(body, tokenHeader);
        ResponseEntity<String> responseEntity = rest.exchange(OAUTH2_TOKEN_API, HttpMethod.POST, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    public String viewToken() {
       return token(SCOPE_VIEW);
    }

    public String manageToken() {
        return token(SCOPE_MANAGE);
    }

    public String foo(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
        headers.add("Authorization", String.format("Bearer %s", token));
        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);
        String url = String.format("%s/%s/%s", DOCUMENT_SERVICE, TENNAT, CLIENT_NAME);
        LOG.debug(url);
        ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.GET, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    public String createWishlist(String json, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
        headers.add("Authorization", String.format("Bearer %s", token));
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        String url = String.format("%s/%s/%s/data/%s", DOCUMENT_SERVICE, TENNAT, CLIENT_NAME, PREWLS);
        LOG.debug(url);
        ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.POST, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
