package com.sap.cc.library.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @BeforeEach
    void clearDB(){
        repository.deleteAll();
    }

    @Test
    void shouldTest_findAll(){
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    void shouldCheckSavedBook(){
        Author author = new Author("Krishna");
        Book book = new Book("Once upon a time", author);
        repository.save(book);

        List<Book> books = repository.findAll();
        assertThat(books.size()).isEqualTo(1);
        assertThat(books.get(0).getTitle()).isEqualTo("Once upon a time");
        assertThat(books.get(0).getAuthor().getName()).isEqualTo("Krishna");
    }

    @Test
    void shouldCheckFindBooksByTitle(){
        Author author1 = new Author("Krishna");
        Author author2 = new Author("Krishnaji Rao");

        Book book1 = new Book("Once upon a time", author1);
        repository.save(book1);

        Book book2 = new Book("Once upon an another time", author2);
        repository.save(book2);

        Book dbBook = repository.findByTitle("Once upon an another time");
        assertThat(dbBook.getTitle()).isEqualTo("Once upon an another time");
    }

}

//docker run --rm --name some-postgres -p 5432:5432 -e POSTGRES_PASSWORD=pw -d postgres
