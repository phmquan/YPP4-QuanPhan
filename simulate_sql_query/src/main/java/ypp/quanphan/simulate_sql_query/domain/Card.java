package ypp.quanphan.simulate_sql_query.domain;

public class Card {
    private long id;
    private String cardName;
    private long stageId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public long getStageId() {
        return stageId;
    }

    public void setStageId(long stageId) {
        this.stageId = stageId;
    }

    public Card(long id, String cardName, long stageId) {
        this.id = id;
        this.cardName = cardName;
        this.stageId = stageId;
    }

}
