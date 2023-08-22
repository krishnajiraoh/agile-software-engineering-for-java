package com.sap.cc.books;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private BookStorage bookStorage;

    public BookController(BookStorage bookStorage) {
        this.bookStorage = bookStorage;
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return bookStorage.getAll();
    }


    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book, HttpServletRequest request) throws URISyntaxException {
        Book createdBook = bookStorage.save(book);
        //createdBook.setId(1L);

        /*
           UriComponents uriComponents = UriComponentsBuilder
            .fromPath("/my/awesome/path" + "/{id}")
            .buildAndExpand(createdEntity.getId());
            URI locationHeaderUri = new URI(uriComponents.getPath());
        */

        /*URI location = ServletUriComponentsBuilder.fromRequestUri(request)
                //.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(createdBook.getId())
                .toUri();*/

        String location = request.getRequestURI() + "/" + createdBook.getId();
        URI uri = new URI(location);

        return ResponseEntity.created(uri).body(createdBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getSingle(@PathVariable("id") Long id){
        if(id < 1){
            throw new IllegalArgumentException();
        }
        Optional op = bookStorage.get(id);
        if(op.isPresent()) {
            Book book = (Book) op.get();
            return ResponseEntity.status(200).body(book);
        }
        else
            throw new NotFoundException();
    }

}

