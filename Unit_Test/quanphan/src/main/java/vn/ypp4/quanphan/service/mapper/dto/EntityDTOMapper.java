package vn.ypp4.quanphan.service.mapper.dto;

import java.util.function.Function;

import org.springframework.stereotype.Service;

@Service
public class EntityDTOMapper {
    public <T, R> R toDTO(T entity, Function<T, R> mapper) {
        return mapper.apply(entity);
    }
}
