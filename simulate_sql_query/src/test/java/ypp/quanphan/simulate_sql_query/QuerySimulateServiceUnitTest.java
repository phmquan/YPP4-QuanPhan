package ypp.quanphan.simulate_sql_query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import ypp.quanphan.simulate_sql_query.domain.Card;
import ypp.quanphan.simulate_sql_query.domain.PairEntity;
import ypp.quanphan.simulate_sql_query.domain.Stage;

import ypp.quanphan.simulate_sql_query.service.QuerySimulateServiceImpl;

@SpringBootTest
public class QuerySimulateServiceUnitTest {

    @InjectMocks
    private QuerySimulateServiceImpl querySimulateServiceImpl;

    private List<Card> cards;
    private List<Stage> stages;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cards = Arrays.asList(
                new Card(1, "Card 1", 1),
                new Card(2, "Card 2", 1));
        stages = Arrays.asList(
                new Stage(1, "Stage 1"),
                new Stage(2, "Stage 2"));

    }

    @Test
    void testCrossJoin() {
        List<PairEntity<Card, Stage>> expected = new ArrayList<>();
        for (Stage stage : stages) {
            for (Card card : cards) {
                expected.add(new PairEntity<Card, Stage>(card, stage));
            }
        }
        List<PairEntity<Card, Stage>> result = querySimulateServiceImpl.crossJoin(cards, stages);
        assertEquals(4, result.size()); // 2 cards * 2 stages
    }

    @Test
    void testInnerJoin() {

    }

}
