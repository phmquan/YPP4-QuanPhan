package vn.ypp4.quanphan.repository.interf;

import vn.ypp4.quanphan.domain.Board;

import java.util.List;

public interface UserViewHistoryRepository {
    List<Board> findRecentlyViewedBoardByUserId(int userId,int numBoardRequested);
}
