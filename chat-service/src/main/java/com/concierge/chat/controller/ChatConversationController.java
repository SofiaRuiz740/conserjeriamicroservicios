package com.concierge.chat.controller;

import com.concierge.chat.dto.*;
import com.concierge.chat.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chat-service/conversations")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ChatConversationController {

    private final ChatService chatService;

    public ChatConversationController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ConversationDTO> createConversation(
            @RequestBody Map<String, String> request,
            Authentication authentication) {
        Long userId = Long.parseLong(authentication.getDetails().toString());
        String title = request.getOrDefault("title", "New Conversation");
        ConversationDTO response = chatService.createConversation(userId, title);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ConversationDTO>> getUserConversations(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getDetails().toString());
        List<ConversationDTO> conversations = chatService.getUserConversations(userId);
        return ResponseEntity.ok(conversations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConversationDTO> getConversationById(@PathVariable Long id) {
        ConversationDTO conversation = chatService.getConversationById(id);
        return ResponseEntity.ok(conversation);
    }

    @PostMapping("/{id}/messages")
    public ResponseEntity<ChatMessageDTO> sendMessage(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String content = request.get("content");
        ChatMessageDTO response = chatService.sendMessage(id, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<List<ChatMessageDTO>> getConversationMessages(@PathVariable Long id) {
        List<ChatMessageDTO> messages = chatService.getConversationMessages(id);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Chat Service is running");
    }
}
