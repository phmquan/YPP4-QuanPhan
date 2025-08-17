package vn.ypp4.quanphan.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.domain.dto.template.TemplateResponseDTO;
import vn.ypp4.quanphan.domain.entity.Template;
import vn.ypp4.quanphan.domain.entity.TemplateCategory;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TemplateRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<TemplateCategory> findCategory(int numCategoryRequested) {
        String sql="SELECT \n" +
                "    tpc.Id,\n" +
                "    tpc.DisplayValue,\n" +
                "    tpc.IconUrl\n" +
                "FROM \n" +
                "    TemplateCategory tpc \n" +
                "LIMIT ?";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(TemplateCategory.class),
                numCategoryRequested
                );
    }

    public List<TemplateResponseDTO> getTemplate(int numTemplateRequest) {
        String sql="SELECT \n" +
                "    t.Id as TemplateId,\n" +
                "    t.Title, \n" +
                "    t.Copied,\n" +
                "    t.Viewed,\n" +
                "    t.BackgroundUrl,\n" +
                "    t.TemplateDescription,\n" +
                "    u.FullName AS AuthorName,\n" +
                "    u.Avatar AS AuthorAvatar,\n" +
                "FROM Template t \n" +
                "    JOIN Users u  ON t.CreatedBy = u.Id\n" +
                "ORDER BY \n" +
                "    t.CreatedAt DESC, \n" +
                "    t.Viewed DESC, \n" +
                "    t.Copied DESC\n" +
                "LIMIT ?;  \n";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(TemplateResponseDTO.class),
                numTemplateRequest
                );
    }

    public TemplateResponseDTO getTemplateDetail(int templateId) {
        String sql = "SELECT \n" +
                "    t.Id as TemplateId,\n" +
                "    t.Title, \n" +
                "    t.Copied,\n" +
                "    t.Viewed,\n" +
                "    t.BackgroundUrl,\n" +
                "    t.TemplateDescription,\n" +
                "    u.FullName AS AuthorName,\n" +
                "    u.Avatar AS AuthorAvatar,\n" +
                "FROM Template t \n" +
                "    JOIN Users u  ON t.CreatedBy = u.Id\n" +
                "WHERE t.Id = ?";

        return jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<>(TemplateResponseDTO.class)
                , templateId);
    }

    public boolean existsById(int templateId) {
        String sql = "SELECT COUNT(*) FROM Template  WHERE Id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, templateId);
        return count != null && count > 0;
    }
}
