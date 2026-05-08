package com.concierge.request.dto;

import com.concierge.request.model.Priority;
import com.concierge.request.model.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConciergeRequestDTO {

    private Long id;
    private Long userId;
    private Long categoryId;
    private ServiceCategoryDTO category;
    private String title;
    private String description;
    private RequestStatus status;
    private Priority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
}
