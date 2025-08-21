package vn.ypp4.quanphan.dto.member;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberWorkspaceResponseDTO {
    private int userId;
    private String username;
    private String UserEmail;
    private LocalDateTime lastActive;
    private String permission;
    private String NumBoardJoined;
    private String joinedBoardNames;
    private String joinedBoardBackground;
}
