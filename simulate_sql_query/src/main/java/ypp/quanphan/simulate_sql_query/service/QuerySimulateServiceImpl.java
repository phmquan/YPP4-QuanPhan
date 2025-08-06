package ypp.quanphan.simulate_sql_query.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import ypp.quanphan.simulate_sql_query.domain.PairEntity;

public class QuerySimulateServiceImpl implements QuerySimulateService {

    @Override
    public <L, R> Map<String, Object> aggregateFunctions(List<L> leftEntities, List<R> rightEntities,
            Function<L, Number> leftNumericField, Function<R, Number> rightNumericField) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <L, R> List<PairEntity<L, R>> crossJoin(List<L> leftEntities, List<R> rightEntities) {
        List<PairEntity<L, R>> result = new ArrayList<>();
        for (L left : leftEntities) {
            for (R right : rightEntities) {
                result.add(new PairEntity<L, R>(left, right));
            }
        }
        return result;
    }

    @Override
    public <L> List<L> filter(List<L> entities, Predicate<L> condition) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <L, R> List<PairEntity<L, R>> fullOuterJoin(List<L> leftEntities, List<R> rightEntities,
            BiFunction<L, R, Boolean> joinCondition) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
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

    @Override
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

    @Override
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
