# Java

Explain Static methods, abstract methods and default methods of interface

Design Patterns:

- [https://app.pluralsight.com/skilliq/java-design-patterns/intro?context=skills](https://app.pluralsight.com/skilliq/java-design-patterns/intro?context=skills)

Integer to String:

- Integer.toString(i);

Compare two String:

- s1.equals(s2)
    - == doesn’t work

Class name should nouns, methods name should be verbs

### Optional Class:

- get()

```java
public Optional<Book> get(Long id) {
		return Optional.ofNullable(books.get(id));
	}

Optional op = bookStorage.get(id);
        Book book = (Book) op.get();
```

JUnits:

- Test Isolation for autowired classes
- Dirties Context
    - @DirtiesContext can help us clean up any state we add/apply to the spring context through test cases.
    - It will throw away and re-create the whole Spring context of the application after every single test method (or class).
    - Use this feature sparingly, since it will significantly increase the runtime of your tests. More often than not there are other, way more performant, ways of "cleaning up" the state your tests created.
    
    ```java
    /*
    The following annotation simulates application restart before every test method
    */
    @SpringBootTest
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    public class DataDurabilityTest { ....
    ```
    
    [DirtiesContext](https://docs.spring.io/spring-framework/docs/3.0.x/javadoc-api/org/springframework/test/annotation/DirtiesContext.html)
    
- AssertThat → AssertJ
    
    ```java
    import static org.assertj.core.api.Assertions.assertThat;
    ```
    
    ```java
    *assertThat*(list).hasSizeGreaterThanOrEqualTo(2);
    ```
    
    ### SpringBoot:
    
    - Unit Testing
        - the @SpringBootTest annotation registers this test class with Spring Boot which will, among other things, take care of dependency injection for this class
        - the @AutoConfigureMockMvc annotation triggers auto-configuration for MockMvc which is then injected below
        - MockMvc will be used to send our "spring-internal" requests to our implementation under test
        
        ```java
        package com.sap.cc.books;
        
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.test.web.servlet.MockMvc;
        
        @SpringBootTest
        @AutoConfigureMockMvc
        public class BookControllerTest {
        
            @Autowired
            private MockMvc mockMvc;
        
        }
        ```
        
        ### Custom exception Mapping:
        
        add a custom exception mapper to map the IllegalArgumentException to a BAD_REQUEST(400) return code. Simply copy the code below and create the corresponding class
        
        ```java
        package com.sap.cc.books;
        
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.ExceptionHandler;
        import org.springframework.web.bind.annotation.RestControllerAdvice;
        
        @RestControllerAdvice
        public class CustomExceptionMapper {
        
            @ExceptionHandler
            public ResponseEntity<String> handleBadRequestException(IllegalArgumentException exception) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getLocalizedMessage());
            }
        
        }
        ```
        
        ## H2
        
        - H2 is an open-source lightweight Java database.
        - It can be embedded in Java applications or run in the client-server mode.
        - Mainly, H2 database can be configured to run as inmemory database, which means that data will not persist on the disk. Because of embedded database it is not used for production development, but mostly used for development and testing.
        
        ```xml
        <dependency>
        			<groupId>com.h2database</groupId>
        			<artifactId>h2</artifactId>
        			<scope>test</scope>
        </dependency>
        ```
        
        - By default Spring Boot will configure the H2 database to store its data in-memory
        - Let's reconfigure the H2 database to use file-based storage instead of in-memory storage in order to make our data persistent across application restarts / context reloads.
        
        1. In src/test/resources there is an application.properties file (if not you can simply create it).
        2. In it add these two configurations
        
        ```java
        spring.datasource.url=jdbc:h2:file:./data/book-db
        spring.jpa.hibernate.ddl-auto=update
        ```
        
        - The line ***spring.jpa.hibernate.ddl-auto=update*** is required since the default value would be drop-create
        
        ## CFenv lib
        
        - automatically supplies your app with the connection info from VCAP_SERVICES env variable
        
        ```xml
        <dependency>
            <groupId>io.pivotal.cfenv</groupId>
            <artifactId>java-cfenv-boot</artifactId>
            <version>2.2.2.RELEASE</version>
        </dependency>
        ```
