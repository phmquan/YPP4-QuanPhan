package vn.ypp4.quanphan.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserStarredBoardSectionDTO {
    List<BoardDTO> starredBoard;
}
