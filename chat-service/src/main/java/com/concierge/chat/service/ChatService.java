package com.concierge.chat.service;

import com.concierge.chat.dto.*;
import com.concierge.chat.model.*;
import com.concierge.chat.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChatService {

    private final ChatConversationRepository conversationRepository;
    private final ChatMessageRepository messageRepository;
    private final RecommendationRepository recommendationRepository;
    private final AIResponseService aiResponseService;

    public ChatService(ChatConversationRepository conversationRepository,
                      ChatMessageRepository messageRepository,
                      RecommendationRepository recommendationRepository,
                      AIResponseService aiResponseService) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.recommendationRepository = recommendationRepository;
        this.aiResponseService = aiResponseService;
    }

    public ConversationDTO createConversation(Long userId, String title) {
        ChatConversation conversation = new ChatConversation();
        conversation.setUserId(userId);
        conversation.setTitle(title);

        ChatConversation savedConversation = conversationRepository.save(conversation);
        return mapToDTO(savedConversation, null);
    }

    public List<ConversationDTO> getUserConversations(Long userId) {
        return conversationRepository.findByUserId(userId)
                .stream()
                .map(conv -> mapToDTO(conv, null))
                .collect(Collectors.toList());
    }

    public ConversationDTO getConversationById(Long conversationId) {
        ChatConversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        List<ChatMessage> messages = messageRepository.findByConversationId(conversationId);
        List<ChatMessageDTO> messageDTOs = messages.stream()
                .map(this::mapMessageToDTO)
                .collect(Collectors.toList());

        return mapToDTO(conversation, messageDTOs);
    }

    public ChatMessageDTO sendMessage(Long conversationId, String content) {
        ChatConversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        // Save user message
        ChatMessage userMessage = new ChatMessage();
        userMessage.setConversation(conversation);
        userMessage.setSender(MessageType.USER);
        userMessage.setContent(content);
        ChatMessage savedUserMessage = messageRepository.save(userMessage);

        // Generate AI response
        String aiResponse = aiResponseService.generateResponse(content);

        // Save AI message
        ChatMessage aiMessage = new ChatMessage();
        aiMessage.setConversation(conversation);
        aiMessage.setSender(MessageType.AI);
        aiMessage.setContent(aiResponse);
        messageRepository.save(aiMessage);

        return mapMessageToDTO(savedUserMessage);
    }

    public List<ChatMessageDTO> getConversationMessages(Long conversationId) {
        return messageRepository.findByConversationId(conversationId)
                .stream()
                .map(this::mapMessageToDTO)
                .collect(Collectors.toList());
    }

    public RecommendationDTO addRecommendation(Long conversationId, RecommendationDTO dto) {
        ChatConversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        Recommendation recommendation = new Recommendation();
        recommendation.setConversation(conversation);
        recommendation.setType(dto.getType());
        recommendation.setTitle(dto.getTitle());
        recommendation.setDescription(dto.getDescription());
        recommendation.setLocation(dto.getLocation());
        recommendation.setRating(dto.getRating());

        Recommendation savedRecommendation = recommendationRepository.save(recommendation);
        return mapRecommendationToDTO(savedRecommendation);
    }

    public List<RecommendationDTO> getConversationRecommendations(Long conversationId) {
        return recommendationRepository.findByConversationId(conversationId)
                .stream()
                .map(this::mapRecommendationToDTO)
                .collect(Collectors.toList());
    }

    private ConversationDTO mapToDTO(ChatConversation conversation, List<ChatMessageDTO> messages) {
        ConversationDTO dto = new ConversationDTO();
        dto.setId(conversation.getId());
        dto.setUserId(conversation.getUserId());
        dto.setTitle(conversation.getTitle());
        dto.setStartedAt(conversation.getStartedAt());
        dto.setUpdatedAt(conversation.getUpdatedAt());
        dto.setMessages(messages);
        return dto;
    }

    private ChatMessageDTO mapMessageToDTO(ChatMessage message) {
        return new ChatMessageDTO(message.getId(), message.getConversation().getId(),
                message.getSender(), message.getContent(), message.getTimestamp());
    }

    private RecommendationDTO mapRecommendationToDTO(Recommendation recommendation) {
        return new RecommendationDTO(recommendation.getId(),
                recommendation.getConversation().getId(),
                recommendation.getType(), recommendation.getTitle(),
                recommendation.getDescription(), recommendation.getLocation(),
                recommendation.getRating());
    }
}
