package vn.ypp4.quanphan.service.impl.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import vn.ypp4.quanphan.domain.CategoryType;
import vn.ypp4.quanphan.service.mapper.CategoryTypeRowMapper;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryTypeServiceImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CategoryTypeRowMapper categoryTypeRowMapper;

    public List<CategoryType> findAll() {
        String sql = "SELECT Id, CategoryTypeValue FROM CategoryType";
        return jdbcTemplate.query(sql, categoryTypeRowMapper);
    }

    public Optional<CategoryType> findById(int id) {
        String sql = "SELECT Id, CategoryTypeValue FROM CategoryType WHERE Id = ?";
        List<CategoryType> categoryTypes = jdbcTemplate.query(sql, categoryTypeRowMapper, id);
        return categoryTypes.isEmpty() ? Optional.empty() : Optional.of(categoryTypes.get(0));
    }

    public CategoryType save(CategoryType categoryType) {
        if (categoryType.getId() == 0) {
            return create(categoryType);
        } else {
            return update(categoryType);
        }
    }

    private CategoryType create(CategoryType categoryType) {
        String sql = "INSERT INTO CategoryType (CategoryTypeValue) VALUES (?)";

        jdbcTemplate.update(sql,
                categoryType.getCategoryTypeValue());

        return categoryType;
    }

    private CategoryType update(CategoryType categoryType) {
        String sql = "UPDATE CategoryType SET CategoryTypeValue = ? WHERE Id = ?";

        jdbcTemplate.update(sql,
                categoryType.getCategoryTypeValue(),
                categoryType.getId());

        return categoryType;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM CategoryType WHERE Id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Optional<CategoryType> findByCategoryTypeValue(String categoryTypeValue) {
        String sql = "SELECT Id, CategoryTypeValue FROM CategoryType WHERE CategoryTypeValue = ?";
        List<CategoryType> categoryTypes = jdbcTemplate.query(sql, categoryTypeRowMapper, categoryTypeValue);
        return categoryTypes.isEmpty() ? Optional.empty() : Optional.of(categoryTypes.get(0));
    }
}
