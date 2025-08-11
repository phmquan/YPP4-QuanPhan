package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.Reaction;

public class ReactionRowMapper extends BaseRowMapper<Reaction> {

    @Override
    protected Reaction mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new Reaction(
                rs.getInt("Id"),
                rs.getString("ReactionsName"),
                rs.getString("ShortCode"),
                rs.getInt("CategoryId"),
                rs.getString("Icon"));
    }
}
