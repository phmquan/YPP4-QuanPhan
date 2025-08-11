package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.Board;
import vn.ypp4.quanphan.util.constant.BoardStatusEnum;

public class BoardRowMapper extends BaseRowMapper<Board> {

    @Override
    protected Board mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        Board board = new Board();
        board.setId(rs.getInt("Id"));
        board.setBoardName(rs.getString("BoardName"));
        board.setBoardDescription(rs.getString("BoardDescription"));
        board.setCreatedAt(
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null);
        board.setCreatedBy(rs.getInt("CreatedBy"));
        board.setBackgroundUrl(rs.getString("BackgroundUrl"));
        board.setWorkspaceId(rs.getInt("WorkspaceId"));
        board.setBoardStatus(BoardStatusEnum.valueOf(rs.getString("BoardStatus")));
        board.setUpdatedAt(
                rs.getTimestamp("UpdatedAt") != null ? rs.getTimestamp("UpdatedAt").toLocalDateTime() : null);
        board.setUpdatedBy(rs.getInt("UpdatedBy"));
        return board;
    }
}
