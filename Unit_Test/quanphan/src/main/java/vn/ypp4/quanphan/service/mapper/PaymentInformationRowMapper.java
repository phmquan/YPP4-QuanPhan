package vn.ypp4.quanphan.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.PaymentInformation;

public class PaymentInformationRowMapper extends BaseRowMapper<PaymentInformation> {

    @Override
    protected PaymentInformation mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new PaymentInformation(
                rs.getInt("Id"),
                rs.getInt("BillingContactId"),
                rs.getString("CardNumber"),
                rs.getString("CardBrand"),
                rs.getDate("ExpirationDate") != null ? rs.getDate("ExpirationDate").toLocalDate() : null,
                rs.getString("Cvv"),
                rs.getString("Country"),
                rs.getString("PostalCode"));
    }
}
