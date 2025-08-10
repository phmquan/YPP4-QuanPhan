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
public class Attachment {
    private int id;
    private int cardId;
    private int attachmentTypeId;
    private String attachmentPath;
    private String attachmentName;
    private LocalDateTime createdAt;
    private int createdBy;
    private String size;
    private boolean isCover;
}
