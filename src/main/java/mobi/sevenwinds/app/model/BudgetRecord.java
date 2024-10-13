package mobi.sevenwinds.app.model;


import com.papsign.ktor.openapigen.annotations.type.number.integer.max.Max;
import com.papsign.ktor.openapigen.annotations.type.number.integer.min.Min;
import mobi.sevenwinds.app.budget.BudgetType;


    public class BudgetRecord {
        @Min(1900)
        private int year;

        @Min(1)
        @Max(12)
        private int month;

        @Min(1)
        private int amount;

        private BudgetType type;

        public BudgetRecord(int year, int month, int amount, BudgetType type) {
            this.year = year;
            this.month = month;
            this.amount = amount;
            this.type = type;
        }

        public int getYear() { return year; }
        public int getMonth() { return month; }
        public int getAmount() { return amount; }
        public BudgetType getType() { return type; }
    }

