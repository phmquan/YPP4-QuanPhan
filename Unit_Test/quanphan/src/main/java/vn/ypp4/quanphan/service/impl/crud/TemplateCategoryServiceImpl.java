package vn.ypp4.quanphan.service.impl.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import vn.ypp4.quanphan.domain.TemplateCategory;
import vn.ypp4.quanphan.service.mapper.TemplateCategoryRowMapper;

import java.util.List;
import java.util.Optional;

@Service
public class TemplateCategoryServiceImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TemplateCategoryRowMapper templateCategoryRowMapper;

    public List<TemplateCategory> findAll() {
        String sql = "SELECT Id, CategoryValue, DisplayValue, IconUrl FROM TemplateCategory";
        return jdbcTemplate.query(sql, templateCategoryRowMapper);
    }

    public Optional<TemplateCategory> findById(int id) {
        String sql = "SELECT Id, CategoryValue, DisplayValue, IconUrl FROM TemplateCategory WHERE Id = ?";
        List<TemplateCategory> templateCategories = jdbcTemplate.query(sql, templateCategoryRowMapper, id);
        return templateCategories.isEmpty() ? Optional.empty() : Optional.of(templateCategories.get(0));
    }

    public TemplateCategory save(TemplateCategory templateCategory) {
        if (templateCategory.getId() == 0) {
            return create(templateCategory);
        } else {
            return update(templateCategory);
        }
    }

    private TemplateCategory create(TemplateCategory templateCategory) {
        String sql = "INSERT INTO TemplateCategory (CategoryValue, DisplayValue, IconUrl) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql,
                templateCategory.getCategoryValue(),
                templateCategory.getDisplayValue(),
                templateCategory.getIconUrl());

        return templateCategory;
    }

    private TemplateCategory update(TemplateCategory templateCategory) {
        String sql = "UPDATE TemplateCategory SET CategoryValue = ?, DisplayValue = ?, IconUrl = ? WHERE Id = ?";

        jdbcTemplate.update(sql,
                templateCategory.getCategoryValue(),
                templateCategory.getDisplayValue(),
                templateCategory.getIconUrl(),
                templateCategory.getId());

        return templateCategory;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM TemplateCategory WHERE Id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Optional<TemplateCategory> findByCategoryValue(String categoryValue) {
        String sql = "SELECT Id, CategoryValue, DisplayValue, IconUrl FROM TemplateCategory WHERE CategoryValue = ?";
        List<TemplateCategory> templateCategories = jdbcTemplate.query(sql, templateCategoryRowMapper, categoryValue);
        return templateCategories.isEmpty() ? Optional.empty() : Optional.of(templateCategories.get(0));
    }
}
