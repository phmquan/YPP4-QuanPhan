package vn.ypp4.quanphan.api.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sticker {
    private int id;
    private int categoryId;
    private String stickerName;
    private String stickerUrl;
    private LocalDateTime createdAt;
    private int createdBy;
}
