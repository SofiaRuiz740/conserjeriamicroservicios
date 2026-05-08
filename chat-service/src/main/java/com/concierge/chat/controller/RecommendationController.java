package com.concierge.chat.controller;

import com.concierge.chat.dto.RecommendationDTO;
import com.concierge.chat.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/chat-service/recommendations")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RecommendationController {

    private final ChatService chatService;

    public RecommendationController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<RecommendationDTO> addRecommendation(
            @RequestBody RecommendationDTO dto) {
        RecommendationDTO response = chatService.addRecommendation(dto.getConversationId(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<List<RecommendationDTO>> getRecommendations(@PathVariable Long conversationId) {
        List<RecommendationDTO> recommendations = chatService.getConversationRecommendations(conversationId);
        return ResponseEntity.ok(recommendations);
    }
}
