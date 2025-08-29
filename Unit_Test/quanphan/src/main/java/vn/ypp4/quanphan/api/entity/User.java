package vn.ypp4.quanphan.api.entity;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String fullName;
    private String bio;
    private String email;
    private LocalDateTime lastActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String avatar; // Changed from 'avatar' to match database/RowMapper expectations
}
