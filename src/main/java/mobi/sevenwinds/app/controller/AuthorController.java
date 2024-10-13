package mobi.sevenwinds.app.controller;

import mobi.sevenwinds.app.domain.entity.Author;
import mobi.sevenwinds.app.service.AuthorService;

public class AuthorController {

    @PostMapping("/author/add")
    public ResponseEntity<Author> addAuthor(@RequestBody String fio) {
        Author author = AuthorService.addAuthor(fio);
        return ResponseEntity.ok(author);
    }
}
