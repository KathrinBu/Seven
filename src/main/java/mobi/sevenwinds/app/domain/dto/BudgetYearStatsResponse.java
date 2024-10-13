package mobi.sevenwinds.app.domain.dto;

import mobi.sevenwinds.app.model.BudgetRecord;

import java.util.List;
import java.util.Map;

public class BudgetYearStatsResponse {
    private int total;
    private Map<String, Integer> totalByType;
    private List<BudgetRecord> items;

    public BudgetYearStatsResponse(int total, Map<String, Integer> totalByType, List<BudgetRecord> sortedRecords) {
    }

    public int getTotal() { return total; }

    public Map<String, Integer> getTotalByType() { return totalByType; }
    public List<BudgetRecord> getItems() { return items; }
}
