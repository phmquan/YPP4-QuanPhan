package vn.ypp4.quanphan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private int id;
    private String name;
    private String backgroundUrl;
}
