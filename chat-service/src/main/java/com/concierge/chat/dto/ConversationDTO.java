package com.concierge.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDTO {

    private Long id;
    private Long userId;
    private String title;
    private LocalDateTime startedAt;
    private LocalDateTime updatedAt;
    private List<ChatMessageDTO> messages;
}
