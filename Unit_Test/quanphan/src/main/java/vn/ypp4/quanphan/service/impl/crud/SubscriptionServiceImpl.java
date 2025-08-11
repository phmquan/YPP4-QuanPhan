package vn.ypp4.quanphan.service.impl.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import vn.ypp4.quanphan.domain.Subscription;
import vn.ypp4.quanphan.service.mapper.SubscriptionRowMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SubscriptionRowMapper subscriptionRowMapper;

    public List<Subscription> findAll() {
        String sql = "SELECT Id, BillingContactId, BillingPlanId, StartDate, EndDate, IsMonthly, SubscriptionStatus, AutoRenew, MemberCountBilled FROM Subscription";
        return jdbcTemplate.query(sql, subscriptionRowMapper);
    }

    public Optional<Subscription> findById(int id) {
        String sql = "SELECT Id, BillingContactId, BillingPlanId, StartDate, EndDate, IsMonthly, SubscriptionStatus, AutoRenew, MemberCountBilled FROM Subscription WHERE Id = ?";
        List<Subscription> subscriptions = jdbcTemplate.query(sql, subscriptionRowMapper, id);
        return subscriptions.isEmpty() ? Optional.empty() : Optional.of(subscriptions.get(0));
    }

    public Subscription save(Subscription subscription) {
        if (subscription.getId() == 0) {
            return create(subscription);
        } else {
            return update(subscription);
        }
    }

    private Subscription create(Subscription subscription) {
        String sql = "INSERT INTO Subscription (BillingContactId, BillingPlanId, StartDate, EndDate, IsMonthly, SubscriptionStatus, AutoRenew, MemberCountBilled) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                subscription.getBillingContactId(),
                subscription.getBillingPlanId(),
                subscription.getStartDate(),
                subscription.getEndDate(),
                subscription.isMonthly(),
                subscription.isSubscriptionStatus(),
                subscription.getAutoRenew(),
                subscription.getMemberCountBilled());

        return subscription;
    }

    private Subscription update(Subscription subscription) {
        String sql = "UPDATE Subscription SET BillingContactId = ?, BillingPlanId = ?, StartDate = ?, EndDate = ?, IsMonthly = ?, SubscriptionStatus = ?, AutoRenew = ?, MemberCountBilled = ? WHERE Id = ?";

        jdbcTemplate.update(sql,
                subscription.getBillingContactId(),
                subscription.getBillingPlanId(),
                subscription.getStartDate(),
                subscription.getEndDate(),
                subscription.isMonthly(),
                subscription.isSubscriptionStatus(),
                subscription.getAutoRenew(),
                subscription.getMemberCountBilled(),
                subscription.getId());

        return subscription;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM Subscription WHERE Id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Subscription> findByBillingContactId(int billingContactId) {
        String sql = "SELECT Id, BillingContactId, BillingPlanId, StartDate, EndDate, IsMonthly, SubscriptionStatus, AutoRenew, MemberCountBilled FROM Subscription WHERE BillingContactId = ?";
        return jdbcTemplate.query(sql, subscriptionRowMapper, billingContactId);
    }

    public List<Subscription> findActiveSubscriptions() {
        String sql = "SELECT Id, BillingContactId, BillingPlanId, StartDate, EndDate, IsMonthly, SubscriptionStatus, AutoRenew, MemberCountBilled FROM Subscription WHERE SubscriptionStatus = true AND EndDate >= ?";
        return jdbcTemplate.query(sql, subscriptionRowMapper, LocalDate.now());
    }

    public List<Subscription> findExpiredSubscriptions() {
        String sql = "SELECT Id, BillingContactId, BillingPlanId, StartDate, EndDate, IsMonthly, SubscriptionStatus, AutoRenew, MemberCountBilled FROM Subscription WHERE EndDate < ?";
        return jdbcTemplate.query(sql, subscriptionRowMapper, LocalDate.now());
    }
}
