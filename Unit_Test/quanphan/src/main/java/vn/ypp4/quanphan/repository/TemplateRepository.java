package vn.ypp4.quanphan.repository;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.domain.dto.template.TemplateCategoryResponseDTO;
import vn.ypp4.quanphan.domain.dto.template.TemplateDetailResponseDTO;
import vn.ypp4.quanphan.domain.dto.template.TemplateResponseDTO;
import vn.ypp4.quanphan.domain.dto.user.UserResponseDTO;
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
                "    TemplateCategory tpc\n" +
                "LIMIT ?";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(TemplateCategory.class),
                numCategoryRequested
                );
    }

    public List<Template> getTemplate(int numTemplateRequest) {
        String sql="SELECT \n" +
                "    t.Id as TemplateId,\n" +
                "    t.Title,\n" +
                "    t.BackgroundUrl,\n" +
                "    t.CreatedAt,\n" +
                "    t.CreatedBy,\n" +
                "    t.Copied,\n" +
                "    t.Viewed,\n" +
                "    t.TemplateDescription\n" +
                "FROM Template t\n" +
                "ORDER BY \n" +
                "    t.CreatedAt DESC, \n" +
                "    t.Viewed DESC, \n" +
                "    t.Copied DESC\n" +
                "LIMIT ?;  \n";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(Template.class),
                numTemplateRequest
                );
    }

    public TemplateDetailResponseDTO getTemplateDetail(int templateId) {
        String sql = "SELECT \n" +
                "    t.Id as TemplateId,\n" +
                "    t.Title, \n" +
                "    t.Copied,\n" +
                "    t.Viewed,\n" +
                "    t.TemplateDescription,\n" +
                "    u.FullName,\n" +
                "    u.Username,\n" +
                "    u.Avatar,\n" +
                "    u.Email\n" +
                "FROM Template t\n" +
                "    JOIN Users u ON t.CreatedBy = u.Id\n" +
                "WHERE t.Id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            TemplateDetailResponseDTO template = new TemplateDetailResponseDTO();
            template.setTemplateId(rs.getInt("TemplateId"));
            template.setTitle(rs.getString("Title"));
            template.setCopied(rs.getInt("Copied"));
            template.setViewed(rs.getInt("Viewed"));
            template.setTemplateDescription(rs.getString("TemplateDescription"));

            UserResponseDTO user = new UserResponseDTO();
            user.setFullName(rs.getString("FullName"));
            user.setUserName(rs.getString("Username"));
            user.setAvatar(rs.getString("Avatar"));
            user.setEmail(rs.getString("Email"));

            template.setCreatedBy(user);
            return template;
        }, templateId);
    }

    public boolean existsById(int templateId) {
        String sql = "SELECT COUNT(*) > 0 FROM Template WHERE Id = ?";
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, templateId);
        return exists != null && exists;
    }
}
