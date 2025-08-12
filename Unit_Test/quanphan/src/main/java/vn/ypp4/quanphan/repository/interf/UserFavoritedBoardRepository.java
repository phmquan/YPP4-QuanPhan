package vn.ypp4.quanphan.repository.interf;

import vn.ypp4.quanphan.domain.Board;
import vn.ypp4.quanphan.domain.UserFavoritedBoard;

import java.util.List;

public interface UserFavoritedBoardRepository {
    List<Board> getFavoritedBoardsByUserId(int userId);

    void createUser(UserFavoritedBoard starred);
}
