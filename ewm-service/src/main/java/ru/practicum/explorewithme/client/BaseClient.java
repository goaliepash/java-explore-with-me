package ru.practicum.explorewithme.client;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpMethod.GET;

public class BaseClient {

    private final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected <T> ResponseEntity<Object> post(String path, T body) {
        return makeAndSendRequest(HttpMethod.POST, path, body, null);
    }

    protected ResponseEntity<Object> get(String path, @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(GET, path, null, parameters);
    }

    private <T> ResponseEntity makeAndSendRequest(HttpMethod method, String path, T body, @Nullable Map<String, Object> parameters) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, defaultHeaders());
        ResponseEntity<Object> ewmServerResponse;
        if (parameters != null) {
            ewmServerResponse = rest.exchange(path, method, requestEntity, Object.class, parameters);
        } else {
            ewmServerResponse = rest.exchange(path, method, requestEntity, Object.class);
        }
        return prepareGatewayResponse(ewmServerResponse);
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());
        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }
        return responseBuilder.build();
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}