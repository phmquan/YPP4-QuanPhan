package vn.ypp4.quanphan.api.repository.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import vn.ypp4.quanphan.api.dto.template.TemplateCategoryResponseDTO;
import vn.ypp4.quanphan.api.dto.template.TemplateResponseDTO;

import java.util.List;

@Repository
public class TemplateRepositoryImpl implements TemplateRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String TEMPLATE_PROJECTION=
                    """
                        SELECT\s
                            t.Id as TemplateId,
                            t.Title,
                            t.BackgroundUrl,
                            t.CreatedAt,
                            t.CreatedBy,
                            t.Copied,
                            t.Viewed,
                            t.TemplateDescription,
                            u.Username as AuthorName,
                            u.Avatar as AuthorAvatar
                        FROM Template t
                        JOIN Users u ON u.Id=t.CreatedBy
                    """;
    @Override
    @Cacheable(cacheNames = "templateCategories")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public List<TemplateCategoryResponseDTO> findTemplateCategories(int numCategoryRequest) {
        String sql= """
                SELECT\s
                  tpc.Id,\s
                  tpc.DisplayValue,\s
                  tpc.IconUrl\s
                FROM\s
                  TemplateCategory tpc\s
                LIMIT ?
                """;
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(TemplateCategoryResponseDTO.class),
                numCategoryRequest
                );
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public List<TemplateResponseDTO> findTemplate(int numTemplateRequest) {
        String sql=TEMPLATE_PROJECTION+
                "ORDER BY \n" +
                "    t.CreatedAt DESC, \n" +
                "    t.Viewed DESC, \n" +
                "    t.Copied DESC\n"+
                "LIMIT ?";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(TemplateResponseDTO.class),
                numTemplateRequest);
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public TemplateResponseDTO findTemplateById(int templateId) {
        String sql=TEMPLATE_PROJECTION+
                "WHERE t.Id = ?";
        return jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<>(TemplateResponseDTO.class),
                templateId);
    }
}
