package mobi.sevenwinds.app.domain.dto;

public class BudgetYearParam {
    private int year;
    private int limit;
    private int offset;

    public BudgetYearParam(int year, int limit, int offset) {
        this.year = year;
        this.limit = limit;
        this.offset = offset;
    }

    public int getYear() { return year; }
    public int getLimit() { return limit; }
    public int getOffset() { return offset; }
}
