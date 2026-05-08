package com.concierge.chat.dto;

import com.concierge.chat.model.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {

    private Long id;
    private Long conversationId;
    private MessageType sender;
    private String content;
    private LocalDateTime timestamp;
}
