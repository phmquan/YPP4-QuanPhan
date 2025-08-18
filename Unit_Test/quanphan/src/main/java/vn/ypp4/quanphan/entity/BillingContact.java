package vn.ypp4.quanphan.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillingContact {
    private int id;
    private int userId;
    private int workspaceId;
    private String billingContactName;
    private String billingContactEmail;
    private int billingLanguage;
    private String additionalInvoiceDetail;
}
