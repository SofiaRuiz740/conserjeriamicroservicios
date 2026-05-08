package com.concierge.request.controller;

import com.concierge.request.dto.*;
import com.concierge.request.model.RequestStatus;
import com.concierge.request.service.ConciergeRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/request-service/requests")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ConciergeRequestController {

    private final ConciergeRequestService requestService;

    public ConciergeRequestController(ConciergeRequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public ResponseEntity<ConciergeRequestDTO> createRequest(
            @Valid @RequestBody CreateRequestDTO dto,
            Authentication authentication) {
        Long userId = Long.parseLong(authentication.getDetails().toString());
        ConciergeRequestDTO response = requestService.createRequest(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ConciergeRequestDTO>> getUserRequests(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getDetails().toString());
        List<ConciergeRequestDTO> requests = requestService.getUserRequests(userId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConciergeRequestDTO> getRequestById(@PathVariable Long id) {
        ConciergeRequestDTO request = requestService.getRequestById(id);
        return ResponseEntity.ok(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConciergeRequestDTO> updateRequest(
            @PathVariable Long id,
            @Valid @RequestBody CreateRequestDTO dto) {
        ConciergeRequestDTO response = requestService.updateRequest(id, dto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ConciergeRequestDTO> updateRequestStatus(
            @PathVariable Long id,
            @RequestParam RequestStatus status) {
        ConciergeRequestDTO response = requestService.updateRequestStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<RequestCommentDTO> addComment(
            @PathVariable Long id,
            @RequestParam String message,
            Authentication authentication) {
        Long userId = Long.parseLong(authentication.getDetails().toString());
        RequestCommentDTO comment = requestService.addComment(id, userId, message);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<RequestCommentDTO>> getRequestComments(@PathVariable Long id) {
        List<RequestCommentDTO> comments = requestService.getRequestComments(id);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Request Service is running");
    }
}
