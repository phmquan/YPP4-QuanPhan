package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import vn.ypp4.quanphan.domain.Cards;
@Component
public class CardsRowMapper extends BaseRowMapper<Cards> {

    @Override
    protected Cards mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new Cards(
                rs.getInt("Id"),
                rs.getInt("StageId"),
                rs.getString("Title"),
                rs.getString("CardDescription"),
                rs.getTimestamp("CreatedAt") != null ? rs.getTimestamp("CreatedAt").toLocalDateTime() : null,
                rs.getInt("CreatedBy"),
                rs.getString("CardStatus"),
                rs.getString("CardLocation"),
                rs.getDate("StartDate") != null ? rs.getDate("StartDate").toLocalDate() : null,
                rs.getDate("DueDate") != null ? rs.getDate("DueDate").toLocalDate() : null,
                rs.getInt("CardCoverTypeId"),
                rs.getString("CoverValue"),
                rs.getInt("Position"),
                rs.getTimestamp("UpdatedAt") != null ? rs.getTimestamp("UpdatedAt").toLocalDateTime() : null,
                rs.getInt("UpdatedBy"));
    }
}
