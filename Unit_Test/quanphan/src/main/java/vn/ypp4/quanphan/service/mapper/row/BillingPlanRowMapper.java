package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import vn.ypp4.quanphan.domain.BillingPlan;

public class BillingPlanRowMapper extends BaseRowMapper<BillingPlan> {

    @Override
    protected BillingPlan mapRowInternal(ResultSet rs, int rowNum) throws SQLException {
        return new BillingPlan(
                rs.getInt("Id"),
                rs.getString("PlanName"),
                rs.getString("BillingPlanDescription"),
                rs.getBigDecimal("PricePerUser"),
                rs.getBoolean("IsActive"));
    }
}
