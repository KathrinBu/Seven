package mobi.sevenwinds.app.domain.entity;

import java.time.LocalDateTime;
import javax.persistence.*;
import java.time.LocalDateTime;



@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fio", nullable = false)
    private String fio;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    public Author() {}

    public Author(String fio) {
        this.fio = fio;
        this.creationDate = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}

