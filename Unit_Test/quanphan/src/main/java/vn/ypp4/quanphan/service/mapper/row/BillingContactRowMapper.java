package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import vn.ypp4.quanphan.domain.BillingContact;
@Component
public class BillingContactRowMapper extends BaseRowMapper<BillingContact> {

    @Override
    protected BillingContact mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new BillingContact(
                rs.getInt("Id"),
                rs.getInt("UserId"),
                rs.getInt("WorkspaceId"),
                rs.getString("BillingContactName"),
                rs.getString("BillingContactEmail"),
                rs.getInt("BillingLanguage"),
                rs.getString("AdditionalInvoiceDetail"));
    }
}
