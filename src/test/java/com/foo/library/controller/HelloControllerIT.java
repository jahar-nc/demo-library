package com.foo.library.controller;

import com.foo.library.model.Book;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerIT {

	@Autowired
	private TestRestTemplate template;

    @Test
    public void getHello() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/", String.class);
        assertThat(response.getBody()).isEqualTo("Greetings from Spring Boot!");
    }

	@Test
	public void add() throws Exception {
		ResponseEntity<Long> response = template.postForEntity(
			"/add",
			Book.of(-1, "title", "genre", "author"),
			Long.class
		);
		assertThat(response.getBody()).isEqualTo(1);
		assert false;
	}
}
