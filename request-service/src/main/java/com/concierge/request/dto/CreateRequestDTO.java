package com.concierge.request.dto;

import com.concierge.request.model.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRequestDTO {

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    private String description;

    @NotNull(message = "Priority cannot be null")
    private Priority priority;
}
