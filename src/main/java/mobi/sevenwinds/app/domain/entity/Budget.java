package mobi.sevenwinds.app.domain.entity;

import mobi.sevenwinds.app.model.BudgetRecord;
import mobi.sevenwinds.app.model.BudgetType;

import java.time.LocalDateTime;
import javax.persistence.*;
import java.time.LocalDateTime;



@Entity
@Table(name = "budget")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "month", nullable = false)
    private int month;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private BudgetType type;

    @ManyToOne(optional = true)
    @JoinColumn(name = "author_id")
    private Author author;

    public Budget() {}

    public Budget(int year, int month, int amount, BudgetType type) {
        this.year = year;
        this.month = month;
        this.amount = amount;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BudgetType getType() {
        return type;
    }

    public void setType(BudgetType type) {
        this.type = type;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public BudgetRecord toResponse() {
        String authorFio = (author != null) ? author.getFio() : null;
        LocalDateTime authorCreationDate = (author != null) ? author.getCreationDate() : null;
        return new BudgetRecord(year, month, amount, type, authorFio, authorCreationDate);
    }
}
