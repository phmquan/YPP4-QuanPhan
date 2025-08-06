package ypp.quanphan.simulate_sql_query.service;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import ypp.quanphan.simulate_sql_query.domain.PairEntity;

public interface QuerySimulateService {
    <L, R> List<PairEntity<L, R>> crossJoin(List<L> leftEntities, List<R> rightEntities);

    <L, R> List<PairEntity<L, R>> innerJoin(List<L> leftEntities, List<R> rightEntities,
            BiFunction<L, R, Boolean> joinCondition);

    <L, R> List<PairEntity<L, R>> leftJoin(List<L> leftEntities, List<R> rightEntities,
            BiFunction<L, R, Boolean> joinCondition);

    <L, R> List<PairEntity<L, R>> rightJoin(List<L> leftEntities, List<R> rightEntities,
            BiFunction<L, R, Boolean> joinCondition);

    <L, R> List<PairEntity<L, R>> fullOuterJoin(List<L> leftEntities, List<R> rightEntities,
            BiFunction<L, R, Boolean> joinCondition);

    <L, R> Map<String, Object> aggregateFunctions(List<L> leftEntities, List<R> rightEntities,
            Function<L, Number> leftNumericField, Function<R, Number> rightNumericField);

    <L> List<L> filter(List<L> entities, Predicate<L> condition);
}
