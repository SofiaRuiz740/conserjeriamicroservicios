package com.concierge.gateway.dto;

import com.concierge.gateway.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private String fullName;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
}
