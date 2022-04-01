package com.foo.library.controller;

import com.foo.library.db.BookDAO;
import com.foo.library.model.Book;
import com.foo.library.service.BookService;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
public class HelloController {
	@Autowired
	private BookService bookService;

	@Autowired
	private ProducerTemplate producerTemplate;

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@GetMapping("/list")
	public Book[] list(
		@RequestParam(name = "author", required = false) String author,
		@RequestParam(name = "genre", required = false) String genre
	) {
		List<BookDAO> all_books = bookService.list();
		Stream<BookDAO> books = all_books.stream();
		if (author != null) {
			books = books.filter(book -> author.equals(book.getAuthor()));
		}
		if (genre != null) {
			books = books.filter(book -> genre.equals(book.getGenre()));
		}
		return books.map(Book::from).toArray(Book[]::new);
	}

	@GetMapping("/get")
	public Book get(
		@RequestParam(name="id") long id
	) {
		for (BookDAO book : this.bookService.list()) {
			if (book.getId() == id) {
				return Book.from(book);
			}
		}
		throw new IllegalArgumentException(String.format("no book with id: %s", id));
	}

	@PostMapping("/add")
	public long add(
		@RequestBody Book book
	) {
		return bookService.add(BookDAO.from(book));
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
