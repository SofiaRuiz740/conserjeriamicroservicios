package com.concierge.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Enumeration;

@RestController
public class ProxyController {

    private final RestTemplate restTemplate;

    @Value("${REQUEST_SERVICE_URL:http://localhost:8081}")
    private String requestServiceUrl;

    @Value("${CHAT_SERVICE_URL:http://localhost:8082}")
    private String chatServiceUrl;

    public ProxyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/api/service-a/**")
    public ResponseEntity<byte[]> proxyToRequestService(
            @RequestBody(required = false) byte[] body,
            HttpMethod method,
            HttpServletRequest request) {
        return proxy(requestServiceUrl, body, method, request);
    }

    @RequestMapping("/api/service-b/**")
    public ResponseEntity<byte[]> proxyToChatService(
            @RequestBody(required = false) byte[] body,
            HttpMethod method,
            HttpServletRequest request) {
        return proxy(chatServiceUrl, body, method, request);
    }

    private ResponseEntity<byte[]> proxy(String targetBaseUrl, byte[] body,
                                         HttpMethod method, HttpServletRequest request) {
        String path = request.getRequestURI();
        String queryString = request.getQueryString();

        String targetUrl = targetBaseUrl + path;
        if (queryString != null && !queryString.isEmpty()) {
            targetUrl += "?" + queryString;
        }

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (!headerName.equalsIgnoreCase("host") &&
                !headerName.equalsIgnoreCase("transfer-encoding")) {
                headers.set(headerName, request.getHeader(headerName));
            }
        }

        HttpEntity<byte[]> httpEntity = new HttpEntity<>(body, headers);

        try {
            return restTemplate.exchange(targetUrl, method, httpEntity, byte[].class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsByteArray());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(("Error connecting to backend service: " + e.getMessage()).getBytes());
        }
    }
}
