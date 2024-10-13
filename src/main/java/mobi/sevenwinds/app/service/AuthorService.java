package mobi.sevenwinds.app.service;

import mobi.sevenwinds.app.domain.entity.Author;

import static org.jetbrains.exposed.sql.transactions.ThreadLocalTransactionManagerKt.transaction;

@Service
public class AuthorService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Author addAuthor(String fio) {
        Author author = new Author(fio);
        entityManager.persist(author);
        return author;
    }
}
