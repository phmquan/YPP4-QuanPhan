package vn.ypp4.quanphan.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserViewHistory {
    private int userId;
    private int ownerTypeId;
    private int ownerId;
    private LocalDateTime accessedAt;
}
