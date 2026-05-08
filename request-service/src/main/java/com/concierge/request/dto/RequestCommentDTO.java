package com.concierge.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestCommentDTO {

    private Long id;
    private Long requestId;
    private Long userId;
    private String message;
    private LocalDateTime createdAt;
}
