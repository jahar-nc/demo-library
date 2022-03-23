package com.foo.library.controller;

import com.foo.library.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerITest {

	@Autowired
	private TestRestTemplate template;

    @Test
    public void getHello() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/", String.class);
        assertThat(response.getBody()).isEqualTo("Greetings from Spring Boot!");
    }

	@Test
	public void postAdd() throws Exception {
		Book toAdd = Book.of(-1, "title", "genre", "author");
		ResponseEntity<Long> response = template.postForEntity(
			"/add",
			toAdd,
			Long.class
		);
		Long addedID = response.getBody();
		assertThat(addedID).isNotNull();
		assertThat(addedID).isNotEqualTo(-1);

		Book expected = Book.of(addedID, toAdd.getTitle(), toAdd.getGenre(), toAdd.getAuthor());
		ResponseEntity<Book[]> allBooksResponse = template.getForEntity(
			"/list", Book[].class
		);
		Book[] allBooks = allBooksResponse.getBody();
		assertThat(allBooks).isNotNull();
		List<Book> bookList = asList(allBooks);
		assert bookList.contains(expected);
		assertThat(bookList).contains(expected);
	}
}
