package com.foo.library.controller;

import com.foo.library.model.Book;
import com.foo.library.model.Books;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import java.io.StringReader;
import java.io.StringWriter;

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
	public void jsonAddThenList() throws Exception {
		Book toAdd = Book.of(-1, "title", "genre", "author");
		ResponseEntity<Long> response = template.postForEntity(
			"/add",
			toAdd,
			Long.class
		);
		Long addedID = response.getBody();
		assertThat(addedID).isNotNull();
		assertThat(addedID).isNotEqualTo(-1);

		Book expected = toAdd.clone();
		expected.setId(addedID);

		ResponseEntity<Book[]> allBooksResponse = template.getForEntity(
			"/list", Book[].class
		);
		Book[] allBooks = allBooksResponse.getBody();
		assertThat(allBooks).isNotNull();
		assertThat(allBooks).contains(expected);
	}

	@Test
	public void xmlAddThenList() throws Exception {
		Book toAdd = Book.of(-1, "title", "genre", "author");
		ResponseEntity<String> response = template.postForEntity(
			"/camel/xml/add",
			objectToXmlString(toAdd, Book.class),
			String.class
		);
		Book addedBook = xmlStringToObject(response.getBody(), Book.class);
		assertThat(addedBook).isNotNull();
		long addedID = addedBook.getId();
		assertThat(addedID).isGreaterThan(0);

		ResponseEntity<String> allBooksResponse = template.getForEntity(
			"/camel/xml/list", String.class
		);
		Books allBooks = xmlStringToObject(allBooksResponse.getBody(), Books.class);
		assertThat(allBooks).isNotNull();
		assertThat(allBooks.getBooks()).isNotNull();
		assertThat(allBooks.getBooks()).contains(addedBook);
	}

	static <T> String objectToXmlString(T object, Class<T> clazz) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		StringWriter writer = new StringWriter();
		context.createMarshaller().marshal(object, writer);
		return writer.toString();
	}

	static <T> T xmlStringToObject(String document, Class<T> clazz) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		StringReader reader = new StringReader(document);
		Object object = context.createUnmarshaller().unmarshal(reader);
		return clazz.cast(object);
	}
}
