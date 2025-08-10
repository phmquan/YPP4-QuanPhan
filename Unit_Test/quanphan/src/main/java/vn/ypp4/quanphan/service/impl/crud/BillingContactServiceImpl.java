package vn.ypp4.quanphan.service.impl.crud;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.BillingContact;
import vn.ypp4.quanphan.service.mapper.BillingContactRowMapper;

@Service
@RequiredArgsConstructor
public class BillingContactServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final BillingContactRowMapper billingContactRowMapper;
    public BillingContact createBillingContact(int userId, int workspaceId, String billingContactName,
            String billingContactEmail, int billingLanguage, String additionalInvoiceDetail) {
        if (billingContactName == null || billingContactName.isBlank()) {
            throw new IllegalArgumentException("Billing contact name cannot be null or empty");
        }
        if (billingContactEmail == null || billingContactEmail.isBlank()) {
            throw new IllegalArgumentException("Billing contact email cannot be null or empty");
        }
        
        jdbcTemplate.update(
            "INSERT INTO BillingContact (UserId, WorkspaceId, BillingContactName, BillingContactEmail, BillingLanguage, AdditionalInvoiceDetail) " +
            "VALUES (?, ?, ?, ?, ?, ?)",
            userId, workspaceId, billingContactName, billingContactEmail, billingLanguage, additionalInvoiceDetail);
            
        return jdbcTemplate.queryForObject(
            "SELECT * FROM BillingContact WHERE Id = LAST_INSERT_ID()", 
            billingContactRowMapper);
    }
    public BillingContact getBillingContactById(int id) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM BillingContact WHERE Id = ?",
            billingContactRowMapper,
            id);
    }
    public BillingContact getBillingContactByWorkspace(int workspaceId) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM BillingContact WHERE WorkspaceId = ?",
            billingContactRowMapper,
            workspaceId);
    }
    public List<BillingContact> getBillingContactsByUser(int userId) {
        return jdbcTemplate.query(
            "SELECT * FROM BillingContact WHERE UserId = ? ORDER BY Id",
            billingContactRowMapper,
            userId);
    }
    public int updateBillingContact(int id, String billingContactName, String billingContactEmail, 
            int billingLanguage, String additionalInvoiceDetail) {
        if (billingContactName == null || billingContactName.isBlank()) {
            throw new IllegalArgumentException("Billing contact name cannot be null or empty");
        }
        if (billingContactEmail == null || billingContactEmail.isBlank()) {
            throw new IllegalArgumentException("Billing contact email cannot be null or empty");
        }
        
        return jdbcTemplate.update(
            "UPDATE BillingContact SET BillingContactName = ?, BillingContactEmail = ?, " +
            "BillingLanguage = ?, AdditionalInvoiceDetail = ? WHERE Id = ?",
            billingContactName, billingContactEmail, billingLanguage, additionalInvoiceDetail, id);
    }
    public int deleteBillingContact(int id) {
        return jdbcTemplate.update(
            "DELETE FROM BillingContact WHERE Id = ?",
            id);
    }
}
