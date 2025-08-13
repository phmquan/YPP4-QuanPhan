package vn.ypp4.quanphan.service.mapper.row;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.Reaction;

@Component
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
