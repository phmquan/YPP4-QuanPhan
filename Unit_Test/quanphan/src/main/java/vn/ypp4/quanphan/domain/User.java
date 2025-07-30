package vn.ypp4.quanphan.domain;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String Username;
    private String Bio;
    private String email;
    private Instant LastActive;
    private Instant CreatedAt;
    private String PictureUrl;
}
