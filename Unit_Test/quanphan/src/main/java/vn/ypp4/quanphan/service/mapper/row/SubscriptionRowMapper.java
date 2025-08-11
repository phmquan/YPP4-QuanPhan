package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.Subscription;

public class SubscriptionRowMapper extends BaseRowMapper<Subscription> {

    @Override
    protected Subscription mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new Subscription(
                rs.getInt("Id"),
                rs.getInt("BillingContactId"),
                rs.getInt("BillingPlanId"),
                rs.getDate("StartDate") != null ? rs.getDate("StartDate").toLocalDate() : null,
                rs.getDate("EndDate") != null ? rs.getDate("EndDate").toLocalDate() : null,
                rs.getBoolean("IsMonthly"),
                rs.getBoolean("SubscriptionStatus"),
                rs.getObject("AutoRenew") != null ? rs.getBoolean("AutoRenew") : null,
                rs.getInt("MemberCountBilled"));
    }
}
