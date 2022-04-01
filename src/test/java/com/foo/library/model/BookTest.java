package com.foo.library.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class BookTest {
	@Test
	public void equals() {
		Book a = Book.of(1, "a", "b", "c");
		Book b = Book.of(1, "a", "b", "c");
		assertThat(a).isEqualTo(b);
	}

	@Test
	public void contains() {
		Book a = Book.of(1, "a", "b", "c");
		Book b = Book.of(1, "a", "b", "c");

		List<Book> books = singletonList(a);
		assertThat(books).contains(b);
	}

	@Test
	public void parseJson() throws Exception {
		String document = "{'id': 1, 'genre': 'genre', 'author': 'author', 'title': 'title'}"
			.replace('\'', '"');

		Book book = new ObjectMapper().readValue(document, Book.class);
		assertThat(book).isEqualTo(Book.of(1, "title", "genre", "author"));
	}

	@Test
	public void parseXml() throws Exception {
		String document = "<book><id>1</id><genre>genre</genre><author>author</author><title>title</title></book>";

		JAXBContext context = JAXBContext.newInstance(Book.class);
		Object book = context.createUnmarshaller().unmarshal(new StringReader(document));
		assertThat(book).isEqualTo(Book.of(1, "title", "genre", "author"));
	}

	@Test
	public void serialiseJson() throws Exception {
		Book book = Book.of(1, "a", "b", "c");
		String actual = new ObjectMapper().writer().writeValueAsString(book);
		assertThat(actual).isEqualTo(
			"{'id': 1, 'title': 'a', 'genre': 'b', 'author': 'c'}"
				.replace('\'', '"').replace(" ", "")
		);
	}

	@Test
	public void serialiseXml() throws Exception {
		Book book = Book.of(1, "a", "b", "c");
		JAXBContext context = JAXBContext.newInstance(Book.class);
		StringWriter out = new StringWriter();
		context.createMarshaller().marshal(book, out);
		assertThat(out.toString()).isEqualTo(
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
			+ "<book><author>c</author><genre>b</genre><id>1</id><title>a</title></book>"
		);
	}
}
