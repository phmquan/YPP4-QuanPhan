package vn.ypp4.quanphan.service.dto;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityToDtoMapper {
    public static <E, D> List<D> mapToDto(List<E> entities, Function<E, D> dtoConstructor) {
        return entities.stream()
                .filter(Objects::nonNull)
                .map(dtoConstructor)
                .collect(Collectors.toList());
    }
}

