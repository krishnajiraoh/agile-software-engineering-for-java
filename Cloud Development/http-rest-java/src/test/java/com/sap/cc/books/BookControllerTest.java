package com.sap.cc.books;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookStorage storage;

    @BeforeEach
    public void beforeEach() {
        storage.deleteAll();
    }

    @Test
    void getAll_noBooks_returnsEmptyList() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books"))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("[]");
    }

    @Test
    void addBook_returnsCreatedBook() throws Exception {
        Book book = getNewBook("Krishna");

        String redirectURL = "/api/v1/books/";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
                        .content(asJsonString(book))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        String locationHeader = result.getResponse().getHeader("Location");
        //Book returnedBook = g.fromJson(content, Book.class);
        //assertThat(locationHeader).isEqualTo(redirectURL + returnedBook.getId());
        JSONObject obj = new JSONObject(content);
        assertThat(locationHeader).isEqualTo(redirectURL + obj.getString("id"));
    }


    @Test
    void addBookAndGetSingle_returnsBook() throws Exception {
        Book book = getNewBook("Krishna");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
                .content(asJsonString(book))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String createdBookUri = result.getResponse().getHeader("Location");

        mockMvc.perform(MockMvcRequestBuilders.get(createdBookUri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addMultipleAndGetAll_returnsAddedBooks() throws Exception {
        Book newBook = getNewBook("Krishna");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
                .content(asJsonString(newBook))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        JSONArray books = new JSONArray(content);

        assertThat(books.length()).isEqualTo(1);

        // expects the book in the list to "be equal" to the book returned by the create (POST)
        //Book returnedBook = g.fromJson(books.get(0).toString(), Book.class);
        //assertThat(returnedBook.getAuthor()).isEqualTo(newBook.getAuthor());
        JSONObject obj = new JSONObject(books.get(0).toString());
        assertThat(obj.getString("author")).isEqualTo(newBook.getAuthor());

        // then creates another Book
        Book secondNewBook = getNewBook("Krishnaji Rao");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
                .content(asJsonString(secondNewBook))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        // gets all stored Books via a GET-all request
        // expects the status code of the GET-all to be OK(200)
        result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        // expects the size of the returned list to be 2
        content = result.getResponse().getContentAsString();
        books = new JSONArray(content);

        assertThat(books.length()).isEqualTo(2);
    }

    @Test
    void getSingle_idLessThanOne_returnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getSingle_noBooks_returnsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/1"))
                .andExpect(status().isNotFound());
    }
    public static Book getNewBook(String author){
        Book book = new Book();
        book.setAuthor(author);
        return book;
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}