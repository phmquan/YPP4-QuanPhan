package ypp.quanphan.simulate_sql_query.domain;

public class Stage {
    private long id;
    private String stageName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public Stage(long id, String stageName) {
        this.id = id;
        this.stageName = stageName;
    }

}
