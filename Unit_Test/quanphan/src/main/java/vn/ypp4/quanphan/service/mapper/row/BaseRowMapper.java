package vn.ypp4.quanphan.service.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public abstract class BaseRowMapper<T> implements RowMapper<T> {
    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        return mapRowInternal(rs, rowNum);
    }

    protected abstract T mapRowInternal(ResultSet rs, int rowNum) throws SQLException;
}
