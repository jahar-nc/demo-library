package com.foo.library.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;
import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.as;
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
	public void parseJSON() throws Exception {
		String document = "{'id': 1, 'genre': 'genre', 'author': 'author', 'title': 'title'}"
			.replace('\'', '"');

		Book book = new ObjectMapper().readValue(document, Book.class);
		assertThat(book).isEqualTo(Book.of(1, "title", "genre", "author"));
	}

	@Test
	public void parseXML() throws Exception {
		String document = "<book><id>1</id><genre>genre</genre><author>author</author><title>title</title></book>";
		InputStream inputStream = new ByteArrayInputStream(document.getBytes(StandardCharsets.UTF_8));

		JAXBContext context = JAXBContext.newInstance(Book.class);
		Object book = context.createUnmarshaller().unmarshal(inputStream);
		assertThat(book).isEqualTo(Book.of(1, "title", "genre", "author"));
	}

	@Test
	public void parseXML2() throws Exception {
		String document = "<book><id>1</id><genre>genre</genre><author>author</author><title>title</title></book>";
		JacksonXMLDataFormat dataFormat = new JacksonXMLDataFormat();

		dataFormat.setUnmarshalType(Book.class);
//		dataFormat.getDataFormat().marshal();


	}
}
