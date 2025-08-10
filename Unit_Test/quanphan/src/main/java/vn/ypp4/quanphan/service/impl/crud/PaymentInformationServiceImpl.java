package vn.ypp4.quanphan.service.impl.crud;

import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.PaymentInformation;
import vn.ypp4.quanphan.service.mapper.PaymentInformationRowMapper;

@Service
@RequiredArgsConstructor
public class PaymentInformationServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final PaymentInformationRowMapper paymentInformationRowMapper;
    @Transactional
    public PaymentInformation createPaymentInformation(int billingContactId, String cardNumber, 
                                                     String cardBrand, LocalDate expirationDate, 
                                                     String cvv, String country, String postalCode) {
        
        validatePaymentInformation(billingContactId, cardNumber, cardBrand, expirationDate, cvv, country, postalCode);
        
        // Check if billing contact exists
        Integer billingContactExists = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM BillingContact WHERE Id = ?",
            Integer.class,
            billingContactId);
            
        if (billingContactExists == null || billingContactExists == 0) {
            throw new IllegalArgumentException("Billing contact with ID " + billingContactId + " not found");
        }
        
        // Check if payment information already exists for this billing contact
        PaymentInformation existingInfo = getPaymentInformationByBillingContact(billingContactId);
        if (existingInfo != null) {
            throw new IllegalStateException("Payment information already exists for billing contact ID " + billingContactId);
        }
        
        // Mask card number before storing (last 4 digits only)
        String maskedCardNumber = "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
        
        jdbcTemplate.update(
            "INSERT INTO PaymentInformation (BillingContactId, CardNumber, CardBrand, " +
            "ExpirationDate, CVV, Country, PostalCode) VALUES (?, ?, ?, ?, ?, ?, ?)",
            billingContactId, maskedCardNumber, cardBrand, expirationDate, cvv, country, postalCode);
            
        return jdbcTemplate.queryForObject(
            "SELECT * FROM PaymentInformation WHERE Id = LAST_INSERT_ID()",
            paymentInformationRowMapper);
    }
    public PaymentInformation getPaymentInformationById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM PaymentInformation WHERE Id = ?",
                paymentInformationRowMapper,
                id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }
    public PaymentInformation getPaymentInformationByBillingContact(int billingContactId) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM PaymentInformation WHERE BillingContactId = ?",
                paymentInformationRowMapper,
                billingContactId);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<PaymentInformation> getPaymentInformationByCardBrand(String cardBrand) {
        if (cardBrand == null || cardBrand.isBlank()) {
            throw new IllegalArgumentException("Card brand cannot be null or empty");
        }
        
        return jdbcTemplate.query(
            "SELECT * FROM PaymentInformation WHERE CardBrand = ? ORDER BY Id",
            paymentInformationRowMapper,
            cardBrand);
    }
    public List<PaymentInformation> getAllPaymentInformation() {
        return jdbcTemplate.query(
            "SELECT * FROM PaymentInformation ORDER BY Id",
            paymentInformationRowMapper);
    }
    @Transactional
    public int updatePaymentInformation(int id, Integer billingContactId, String cardNumber, 
                                      String cardBrand, LocalDate expirationDate, 
                                      String cvv, String country, String postalCode) {
        
        // Get existing payment information
        PaymentInformation existingInfo = getPaymentInformationById(id);
        if (existingInfo == null) {
            throw new IllegalArgumentException("Payment information with ID " + id + " not found");
        }
        
        // Use existing values if not provided in the update
        int finalBillingContactId = billingContactId != null ? billingContactId : existingInfo.getBillingContactId();
        String finalCardNumber = cardNumber != null ? cardNumber : existingInfo.getCardNumber();
        String finalCardBrand = cardBrand != null ? cardBrand : existingInfo.getCardBrand();
        LocalDate finalExpirationDate = expirationDate != null ? expirationDate : existingInfo.getExpirationDate();
        String finalCvv = cvv != null ? cvv : existingInfo.getCvv();
        String finalCountry = country != null ? country : existingInfo.getCountry();
        String finalPostalCode = postalCode != null ? postalCode : existingInfo.getPostalCode();
        
        // Validate the updated values
        validatePaymentInformation(finalBillingContactId, finalCardNumber, finalCardBrand, 
                                 finalExpirationDate, finalCvv, finalCountry, finalPostalCode);
        
        // Check if billing contact exists if it's being updated
        if (billingContactId != null && billingContactId != existingInfo.getBillingContactId()) {
            Integer billingContactExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM BillingContact WHERE Id = ?",
                Integer.class,
                billingContactId);
                
            if (billingContactExists == null || billingContactExists == 0) {
                throw new IllegalArgumentException("Billing contact with ID " + billingContactId + " not found");
            }
            
            // Check if payment information already exists for the new billing contact
            PaymentInformation existingForNewBilling = getPaymentInformationByBillingContact(billingContactId);
            if (existingForNewBilling != null && existingForNewBilling.getId() != id) {
                throw new IllegalStateException("Payment information already exists for billing contact ID " + billingContactId);
            }
        }
        
        // Mask card number before storing if it's being updated
        String maskedCardNumber = finalCardNumber;
        if (cardNumber != null && !cardNumber.equals(existingInfo.getCardNumber())) {
            maskedCardNumber = "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
        }
        
        return jdbcTemplate.update(
            "UPDATE PaymentInformation SET BillingContactId = ?, CardNumber = ?, CardBrand = ?, " +
            "ExpirationDate = ?, CVV = ?, Country = ?, PostalCode = ? WHERE Id = ?",
            finalBillingContactId, maskedCardNumber, finalCardBrand, finalExpirationDate, 
            finalCvv, finalCountry, finalPostalCode, id);
    }
    @Transactional
    public int deletePaymentInformation(int id) {
        // Check if payment information exists
        PaymentInformation existingInfo = getPaymentInformationById(id);
        if (existingInfo == null) {
            throw new IllegalArgumentException("Payment information with ID " + id + " not found");
        }
        
        return jdbcTemplate.update(
            "DELETE FROM PaymentInformation WHERE Id = ?",
            id);
    }
    public boolean isCardExpired(int id) {
        PaymentInformation paymentInfo = getPaymentInformationById(id);
        if (paymentInfo == null) {
            throw new IllegalArgumentException("Payment information with ID " + id + " not found");
        }
        
        return paymentInfo.getExpirationDate().isBefore(LocalDate.now());
    }
    public boolean isCardExpiringSoon(int id, int daysThreshold) {
        if (daysThreshold < 0) {
            throw new IllegalArgumentException("Days threshold must be non-negative");
        }
        
        PaymentInformation paymentInfo = getPaymentInformationById(id);
        if (paymentInfo == null) {
            throw new IllegalArgumentException("Payment information with ID " + id + " not found");
        }
        
        LocalDate today = LocalDate.now();
        LocalDate thresholdDate = today.plusDays(daysThreshold);
        
        return !paymentInfo.getExpirationDate().isBefore(today) && 
               !paymentInfo.getExpirationDate().isAfter(thresholdDate);
    }
    
    private void validatePaymentInformation(int billingContactId, String cardNumber, 
                                          String cardBrand, LocalDate expirationDate, 
                                          String cvv, String country, String postalCode) {
        
        if (billingContactId <= 0) {
            throw new IllegalArgumentException("Billing contact ID must be a positive number");
        }
        
        if (cardNumber == null || cardNumber.isBlank()) {
            throw new IllegalArgumentException("Card number cannot be null or empty");
        }
        
        // Basic card number validation (16 digits, may contain spaces or hyphens)
        String cleanedCardNumber = cardNumber.replaceAll("[\\s-]", "");
        if (!cleanedCardNumber.matches("\\d{13,19}")) {
            throw new IllegalArgumentException("Invalid card number format");
        }
        
        if (cardBrand == null || cardBrand.isBlank()) {
            throw new IllegalArgumentException("Card brand cannot be null or empty");
        }
        
        if (expirationDate == null) {
            throw new IllegalArgumentException("Expiration date cannot be null");
        }
        
        if (expirationDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Card is already expired");
        }
        
        if (cvv == null || cvv.isBlank()) {
            throw new IllegalArgumentException("CVV cannot be null or empty");
        }
        
        // CVV validation (3 or 4 digits)
        if (!cvv.matches("\\d{3,4}")) {
            throw new IllegalArgumentException("Invalid CVV format (must be 3 or 4 digits)");
        }
        
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
        
        if (postalCode == null || postalCode.isBlank()) {
            throw new IllegalArgumentException("Postal code cannot be null or empty");
        }
    }
}
