package vn.ypp4.quanphan.service.impl.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import vn.ypp4.quanphan.domain.Category;
import vn.ypp4.quanphan.service.mapper.CategoryRowMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CategoryRowMapper categoryRowMapper;

    public List<Category> findAll() {
        String sql = "SELECT Id, CategoryName, CategoryDescription, CategoryTypeId, CreatedAt, CreatedBy, Icon, Position, IsActive FROM Category";
        return jdbcTemplate.query(sql, categoryRowMapper);
    }

    public Optional<Category> findById(int id) {
        String sql = "SELECT Id, CategoryName, CategoryDescription, CategoryTypeId, CreatedAt, CreatedBy, Icon, Position, IsActive FROM Category WHERE Id = ?";
        List<Category> categories = jdbcTemplate.query(sql, categoryRowMapper, id);
        return categories.isEmpty() ? Optional.empty() : Optional.of(categories.get(0));
    }

    public Category save(Category category) {
        if (category.getId() == 0) {
            return create(category);
        } else {
            return update(category);
        }
    }

    private Category create(Category category) {
        String sql = "INSERT INTO Category (CategoryName, CategoryDescription, CategoryTypeId, CreatedAt, CreatedBy, Icon, Position, IsActive) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        LocalDateTime now = LocalDateTime.now();
        category.setCreatedAt(now);

        jdbcTemplate.update(sql,
                category.getCategoryName(),
                category.getCategoryDescription(),
                category.getCategoryTypeId(),
                category.getCreatedAt(),
                category.getCreatedBy(),
                category.getIcon(),
                category.getPosition(),
                category.isActive());

        return category;
    }

    private Category update(Category category) {
        String sql = "UPDATE Category SET CategoryName = ?, CategoryDescription = ?, CategoryTypeId = ?, Icon = ?, Position = ?, IsActive = ? WHERE Id = ?";

        jdbcTemplate.update(sql,
                category.getCategoryName(),
                category.getCategoryDescription(),
                category.getCategoryTypeId(),
                category.getIcon(),
                category.getPosition(),
                category.isActive(),
                category.getId());

        return category;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM Category WHERE Id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Category> findByCategoryTypeId(int categoryTypeId) {
        String sql = "SELECT Id, CategoryName, CategoryDescription, CategoryTypeId, CreatedAt, CreatedBy, Icon, Position, IsActive FROM Category WHERE CategoryTypeId = ?";
        return jdbcTemplate.query(sql, categoryRowMapper, categoryTypeId);
    }

    public List<Category> findByIsActive(boolean isActive) {
        String sql = "SELECT Id, CategoryName, CategoryDescription, CategoryTypeId, CreatedAt, CreatedBy, Icon, Position, IsActive FROM Category WHERE IsActive = ?";
        return jdbcTemplate.query(sql, categoryRowMapper, isActive);
    }
}
