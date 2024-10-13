package mobi.sevenwinds.app.service;

import mobi.sevenwinds.app.budget.BudgetEntity;
import mobi.sevenwinds.app.domain.entity.Author;
import mobi.sevenwinds.app.domain.entity.Budget;
import mobi.sevenwinds.app.model.BudgetRecord;
import mobi.sevenwinds.app.model.BudgetType;
import mobi.sevenwinds.app.domain.dto.BudgetYearParam;
import mobi.sevenwinds.app.domain.dto.BudgetYearStatsResponse;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.jetbrains.exposed.sql.transactions.ThreadLocalTransactionManagerKt.transaction;

@Service
public class BudgetService {
    @PersistanceContext
    private EntityManager entityManager;

    public static BudgetRecord addRecord(BudgetRecord body, Integer authorId) {
        return transaction(() -> {
            Budget entity = new Budget();
            entity.setYear(body.getYear());
            entity.setMonth(body.getMonth());
            entity.setAmount(body.getAmount());
            entity.setType(body.getType());

            if (authorId != null) {
                Author author = entityManager.find(Author.class, authorId);
                if (author != null) {
                    entity.setAuthor(author);
                } else {
                    throw new IllegalArgumentException("Author not found");
                }
            }
            entityManager.persist(entity);
            return entity.toResponse();
        });
    }

    public static BudgetYearStatsResponse getYearStats(BudgetYearParam param, String authorFioFilter) {
        List<Budget> budgets = entityManager.createQuery("SELECT b FROM Budget b WHERE b.year = :year", Budget.class)
                .setParameter("year", param.getYear())
                .getResultList();

        if (authorFioFilter != null && !authorFioFilter.isEmpty()) {
            budgets = budgets.stream()
                    .filter(budget -> budget.getAuthor() != null &&
                            budget.getAuthor().getFio().toLowerCase().contains(authorFioFilter.toLowerCase()))
                    .collect(Collectors.toList());
        }

        List<BudgetRecord> sortedRecords = budgets.stream()
                .sorted((b1, b2) -> {
                    int monthCompare = Integer.compare(b1.getMonth(), b2.getMonth());
                    if (monthCompare != 0) return monthCompare;
                    return Integer.compare(b2.getAmount(), b1.getAmount());
                })
                .map(Budget::toResponse)
                .collect(Collectors.toList());

        int total = sortedRecords.size();
        Map<String, Integer> totalByType = sortedRecords.stream()
                .collect(Collectors.groupingBy(record -> record.getType().name(),
                        Collectors.summingInt(BudgetRecord::getAmount)));

        return new BudgetYearStatsResponse(total, totalByType, sortedRecords);
    }
}
