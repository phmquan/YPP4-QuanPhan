package vn.ypp4.quanphan.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentReaction {
    private int commentId;
    private int reactionId;
    private int createdBy;
    private LocalDateTime createdAt;
}
