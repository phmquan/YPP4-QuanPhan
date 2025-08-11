package vn.ypp4.quanphan.service.impl.crud;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.BillingPlan;
import vn.ypp4.quanphan.service.mapper.row.BillingPlanRowMapper;

@Service
@RequiredArgsConstructor
public class BillingPlanServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final BillingPlanRowMapper billingPlanRowMapper;

    public BillingPlan createBillingPlan(String planName, String billingPlanDescription,
            BigDecimal pricePerUser, boolean isActive) {
        if (planName == null || planName.isBlank()) {
            throw new IllegalArgumentException("Plan name cannot be null or empty");
        }
        if (pricePerUser == null || pricePerUser.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price per user must be a non-negative value");
        }

        jdbcTemplate.update(
                "INSERT INTO BillingPlan (PlanName, BillingPlanDescription, PricePerUser, IsActive) " +
                        "VALUES (?, ?, ?, ?)",
                planName, billingPlanDescription, pricePerUser, isActive);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM BillingPlan WHERE Id = LAST_INSERT_ID()",
                billingPlanRowMapper);
    }

    public BillingPlan getBillingPlanById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM BillingPlan WHERE Id = ?",
                billingPlanRowMapper,
                id);
    }

    public BillingPlan getBillingPlanByName(String planName) {
        if (planName == null || planName.isBlank()) {
            throw new IllegalArgumentException("Plan name cannot be null or empty");
        }

        return jdbcTemplate.queryForObject(
                "SELECT * FROM BillingPlan WHERE PlanName = ?",
                billingPlanRowMapper,
                planName);
    }

    public List<BillingPlan> getAllBillingPlans() {
        return jdbcTemplate.query(
                "SELECT * FROM BillingPlan ORDER BY Id",
                billingPlanRowMapper);
    }

    public List<BillingPlan> getActiveBillingPlans() {
        return jdbcTemplate.query(
                "SELECT * FROM BillingPlan WHERE IsActive = true ORDER BY Id",
                billingPlanRowMapper);
    }

    public int updateBillingPlan(int id, String planName, String billingPlanDescription,
            BigDecimal pricePerUser, Boolean isActive) {
        if (planName != null && planName.isBlank()) {
            throw new IllegalArgumentException("Plan name cannot be empty");
        }
        if (pricePerUser != null && pricePerUser.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price per user must be a non-negative value");
        }

        // Get existing plan to merge with updates
        BillingPlan existingPlan = getBillingPlanById(id);

        String finalPlanName = planName != null ? planName : existingPlan.getPlanName();
        String finalDescription = billingPlanDescription != null ? billingPlanDescription
                : existingPlan.getBillingPlanDescription();
        BigDecimal finalPrice = pricePerUser != null ? pricePerUser : existingPlan.getPricePerUser();
        boolean finalIsActive = isActive != null ? isActive : existingPlan.isActive();

        return jdbcTemplate.update(
                "UPDATE BillingPlan SET PlanName = ?, BillingPlanDescription = ?, " +
                        "PricePerUser = ?, IsActive = ? WHERE Id = ?",
                finalPlanName, finalDescription, finalPrice, finalIsActive, id);
    }

    public int deleteBillingPlan(int id) {
        return jdbcTemplate.update(
                "DELETE FROM BillingPlan WHERE Id = ?",
                id);
    }
}
