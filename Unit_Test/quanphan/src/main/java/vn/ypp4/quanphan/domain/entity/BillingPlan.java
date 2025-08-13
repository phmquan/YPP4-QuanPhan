package vn.ypp4.quanphan.domain.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillingPlan {
    private int id;
    private String planName;
    private String billingPlanDescription;
    private BigDecimal pricePerUser;
    private boolean isActive;
}
