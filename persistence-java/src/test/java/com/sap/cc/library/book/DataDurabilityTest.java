package com.sap.cc.library.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DataDurabilityTest {

    @Autowired
    private BookRepository repository;

    @Test
    void populateDb(){
        Author author1 = new Author("Krishna");
        Author author2 = new Author("Krishnaji Rao");

        Book book1 = new Book("Once upon a time", author1);
        repository.save(book1);

        Book book2 = new Book("Once upon an another time", author2);
        repository.save(book2);

        List<Book> books = repository.findAll();
        assertThat(books).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void isDbPopulated(){
        List<Book> books = repository.findAll();
        assertThat(books).hasSizeGreaterThanOrEqualTo(2);
    }
}
