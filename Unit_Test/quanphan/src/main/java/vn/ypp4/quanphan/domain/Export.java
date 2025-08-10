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
public class Export {
    private int id;
    private int workspaceId;
    private int createdBy;
    private LocalDateTime createdAt;
    private int size;
}
