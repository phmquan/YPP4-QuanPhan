package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import vn.ypp4.quanphan.domain.AttachmentType;
@Component
public class AttachmentTypeRowMapper extends BaseRowMapper<AttachmentType> {

    @Override
    protected AttachmentType mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new AttachmentType(
                rs.getInt("Id"),
                rs.getString("TypeValue"),
                rs.getString("DisplayValue"));
    }
}
