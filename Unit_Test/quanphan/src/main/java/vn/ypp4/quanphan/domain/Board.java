package vn.ypp4.quanphan.domain;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.util.constant.BoardStatusEnum;

@Data
@AllArgsConstructor
@RequiredArgsConstructor

public class Board {
    private int Id;
    private String BoardName;
    private String BoardDescription;
    private Instant CreatedAt;
    private int CreatedBy;
    private String BackgroundUrl;
    private int WorkspaceId;
    private BoardStatusEnum BoardStatus;
    private Instant UpdatedAt;
    private int UpdatedBy;
}
