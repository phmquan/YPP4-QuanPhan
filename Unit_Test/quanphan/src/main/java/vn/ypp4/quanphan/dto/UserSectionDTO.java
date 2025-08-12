package vn.ypp4.quanphan.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSectionDTO {
    private int id;
    private String name;
    private String avatarUrl;
}
