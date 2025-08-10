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
public class PaymentInformation {
    private int id;
    private int billingContactId;
    private String cardNumber;
    private String cardBrand;
    private LocalDate expirationDate;
    private String cvv;
    private String country;
    private String postalCode;
}
