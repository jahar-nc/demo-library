package com.foo.library.controller;

import com.foo.library.db.BookDAO;
import com.foo.library.model.Book;
import com.foo.library.service.BookService;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
public class HelloController {
	@Autowired
	private BookService bookService;

	@Autowired
	private ProducerTemplate producerTemplate;

//	private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@GetMapping("/list")
	public Book[] list(
		@RequestParam(name = "author", required = false) String author,
		@RequestParam(name = "genre", required = false) String genre
	) {
		Map<String, Object> headers = new HashMap<>();
		headers.put("author", author);
		headers.put("genre", genre);
		Book[] books = producerTemplate.requestBodyAndHeaders(
			"direct:rmq-list",
			"",
			headers,
			Book[].class
		);
		return books;
//		return bookService.list(author, genre).toArray(new Book[0]);
	}

	@GetMapping("/foo")
	public String foo(
		@RequestParam(name = "author", required = false) String author,
		@RequestParam(name = "genre", required = false) String genre
	) {
//		Book book = Book.of(-1, null, genre, author);
		String response  = producerTemplate.requestBody("direct:foo", author, String.class);
		return response;
	}

	@GetMapping("/get")
	public Book get(
		@RequestParam(name="id") long id
	) {
		for (Book book : this.bookService.list()) {
			if (book.getId() == id) {
				return book;
			}
		}
		throw new IllegalArgumentException(String.format("no book with id: %s", id));
	}

	@PostMapping("/add")
	public long add(
		@RequestBody Book book
	) {
//		Object foo = validatorFactory.getValidator().validate(book, Book.class);
		return bookService.add(book);
	}

	@GetMapping(
		value = "/xml/list",
		consumes = {"application/xml"},
		produces = {"application/xml"}
	)
	public Book[] springXMLList(
		@RequestParam(name = "author", required = false) String author,
		@RequestParam(name = "genre", required = false) String genre
	) {
		return this.list(author, genre);
	}

	@PostMapping(
		value = "/xml/add",
		consumes = {"application/xml"},
		produces = {"application/xml"}
	)
	public Book springXMLAdd(
		@RequestBody Book book
	) {
		long id = this.add(book);
		return this.get(id);
	}
}
