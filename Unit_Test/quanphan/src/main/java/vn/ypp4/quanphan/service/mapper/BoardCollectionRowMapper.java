package vn.ypp4.quanphan.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.BoardCollection;

public class BoardCollectionRowMapper extends BaseRowMapper<BoardCollection> {

    @Override
    protected BoardCollection mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new BoardCollection(
                rs.getInt("BoardId"),
                rs.getInt("CollectionId"));
    }
}
