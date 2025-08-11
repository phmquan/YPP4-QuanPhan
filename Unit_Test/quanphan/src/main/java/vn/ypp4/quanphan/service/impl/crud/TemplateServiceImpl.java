package vn.ypp4.quanphan.service.impl.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import vn.ypp4.quanphan.domain.Template;
import vn.ypp4.quanphan.service.mapper.TemplateRowMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TemplateServiceImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TemplateRowMapper templateRowMapper;

    public List<Template> findAll() {
        String sql = "SELECT Id, Title, TemplateDescription, CategoryId, Viewed, Copied, CreatedBy, CreatedAt, UpdatedAt, UpdatedBy, BoardId, BackgroundUrl FROM Template";
        return jdbcTemplate.query(sql, templateRowMapper);
    }

    public Optional<Template> findById(int id) {
        String sql = "SELECT Id, Title, TemplateDescription, CategoryId, Viewed, Copied, CreatedBy, CreatedAt, UpdatedAt, UpdatedBy, BoardId, BackgroundUrl FROM Template WHERE Id = ?";
        List<Template> templates = jdbcTemplate.query(sql, templateRowMapper, id);
        return templates.isEmpty() ? Optional.empty() : Optional.of(templates.get(0));
    }

    public Template save(Template template) {
        if (template.getId() == 0) {
            return create(template);
        } else {
            return update(template);
        }
    }

    private Template create(Template template) {
        String sql = "INSERT INTO Template (Title, TemplateDescription, CategoryId, Viewed, Copied, CreatedBy, CreatedAt, UpdatedAt, UpdatedBy, BoardId, BackgroundUrl) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        LocalDateTime now = LocalDateTime.now();
        template.setCreatedAt(now);
        template.setUpdatedAt(now);

        jdbcTemplate.update(sql,
                template.getTitle(),
                template.getTemplateDescription(),
                template.getCategoryId(),
                template.getViewed(),
                template.getCopied(),
                template.getCreatedBy(),
                template.getCreatedAt(),
                template.getUpdatedAt(),
                template.getUpdatedBy(),
                template.getBoardId(),
                template.getBackgroundUrl());

        return template;
    }

    private Template update(Template template) {
        String sql = "UPDATE Template SET Title = ?, TemplateDescription = ?, CategoryId = ?, Viewed = ?, Copied = ?, UpdatedAt = ?, UpdatedBy = ?, BoardId = ?, BackgroundUrl = ? WHERE Id = ?";

        template.setUpdatedAt(LocalDateTime.now());

        jdbcTemplate.update(sql,
                template.getTitle(),
                template.getTemplateDescription(),
                template.getCategoryId(),
                template.getViewed(),
                template.getCopied(),
                template.getUpdatedAt(),
                template.getUpdatedBy(),
                template.getBoardId(),
                template.getBackgroundUrl(),
                template.getId());

        return template;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM Template WHERE Id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Template> findByCategoryId(int categoryId) {
        String sql = "SELECT Id, Title, TemplateDescription, CategoryId, Viewed, Copied, CreatedBy, CreatedAt, UpdatedAt, UpdatedBy, BoardId, BackgroundUrl FROM Template WHERE CategoryId = ?";
        return jdbcTemplate.query(sql, templateRowMapper, categoryId);
    }

    public void incrementViewed(int id) {
        String sql = "UPDATE Template SET Viewed = Viewed + 1 WHERE Id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void incrementCopied(int id) {
        String sql = "UPDATE Template SET Copied = Copied + 1 WHERE Id = ?";
        jdbcTemplate.update(sql, id);
    }
}
