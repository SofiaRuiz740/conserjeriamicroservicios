package com.concierge.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationDTO {

    private Long id;
    private Long conversationId;
    private String type;
    private String title;
    private String description;
    private String location;
    private Double rating;
}
