package vn.ypp4.quanphan.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.domain.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.service.mapper.row.BoardRowMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;
    private final BoardRowMapper boardRowMapper;
    public List<BoardResponseDTO> getMemberBoardsByUserId(int userId){
        String sql="";
    }
}
