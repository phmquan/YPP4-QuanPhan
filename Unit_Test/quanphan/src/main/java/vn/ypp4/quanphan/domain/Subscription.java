package vn.ypp4.quanphan.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    private int id;
    private int billingContactId;
    private int billingPlanId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isMonthly;
    private boolean subscriptionStatus;
    private Boolean autoRenew;
    private int memberCountBilled;
}
