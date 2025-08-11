package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.BoardPowerUp;

public class BoardPowerUpRowMapper extends BaseRowMapper<BoardPowerUp> {

    @Override
    protected BoardPowerUp mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new BoardPowerUp(
                rs.getInt("BoardId"),
                rs.getInt("PowerUpId"),
                rs.getBoolean("BoardPowerUpStatus"));
    }
}
