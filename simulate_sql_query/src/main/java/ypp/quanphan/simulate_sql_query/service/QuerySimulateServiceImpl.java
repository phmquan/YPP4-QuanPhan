package ypp.quanphan.simulate_sql_query.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ypp.quanphan.simulate_sql_query.domain.PairEntity;

public class QuerySimulateServiceImpl {

    public <L, R> Map<String, Object> aggregateFunctions(List<L> leftEntities, List<R> rightEntities,
            Function<L, Number> leftNumericField, Function<R, Number> rightNumericField) {
        // TODO Auto-generated method stub
        return null;
    }

    public <L, R> List<PairEntity<L, R>> crossJoin(List<L> leftEntities, List<R> rightEntities) {
        List<PairEntity<L, R>> result = new ArrayList<>();
        for (L left : leftEntities) {
            for (R right : rightEntities) {
                result.add(new PairEntity<L, R>(left, right));
            }
        }
        return result;
    }

    public <L> List<L> filter(List<L> entities, Predicate<L> condition) {
        return entities.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    public <L, R> List<PairEntity<L, R>> fullOuterJoin(List<L> leftEntities, List<R> rightEntities,
            BiFunction<L, R, Boolean> joinCondition) {
        List<PairEntity<L, R>> result = new ArrayList<>();
        // Result of left join
        for (L left : leftEntities) {
            boolean hasMatch = false;
            for (R right : rightEntities) {
                if (joinCondition.apply(left, right)) {
                    result.add(new PairEntity<L, R>(left, right));
                    hasMatch = true;
                }
                if (!hasMatch) {
                    result.add(new PairEntity<L, R>(left, null));
                }
            }
        }
        for (R right : rightEntities) {
            boolean hasMatch = false;
            for (L left : leftEntities) {
                if (joinCondition.apply(left, right)) {
                    hasMatch = true;
                    break;
                }
            }
            if (!hasMatch) {
                result.add(new PairEntity<>(null, right));
            }
        }
        return result;
    }

    public <L, R> List<PairEntity<L, R>> innerJoin(List<L> leftEntities, List<R> rightEntities,
            BiFunction<L, R, Boolean> joinCondition) {
        List<PairEntity<L, R>> result = new ArrayList<>();
        for (L left : leftEntities) {
            for (R right : rightEntities) {
                if (joinCondition.apply(left, right)) {
                    result.add(new PairEntity<L, R>(left, right));
                }
            }
        }
        return result;
    }

    public <L, R> List<PairEntity<L, R>> leftJoin(List<L> leftEntities, List<R> rightEntities,
            BiFunction<L, R, Boolean> joinCondition) {
        List<PairEntity<L, R>> result = new ArrayList<>();
        for (L left : leftEntities) {
            Boolean hasMatch = false;
            for (R right : rightEntities) {
                if (joinCondition.apply(left, right)) {
                    result.add(new PairEntity<L, R>(left, right));
                    hasMatch = true;
                }
            }
            if (!hasMatch) {
                result.add(new PairEntity<L, R>(left, null));
            }
        }
        return result;
    }

    public <L, R> List<PairEntity<L, R>> rightJoin(List<L> leftEntities, List<R> rightEntities,
            BiFunction<L, R, Boolean> joinCondition) {
        List<PairEntity<L, R>> result = new ArrayList<>();
        for (R right : rightEntities) {
            Boolean hasMatch = false;
            for (L left : leftEntities) {
                if (joinCondition.apply(left, right)) {
                    result.add(new PairEntity<L, R>(left, right));
                    hasMatch = true;
                }
            }
            if (!hasMatch) {
                result.add(new PairEntity<L, R>(null, right));
            }
        }
        return result;
    }

}
