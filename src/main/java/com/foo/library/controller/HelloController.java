package com.foo.library.controller;

import com.foo.library.db.BookDAO;
import com.foo.library.model.Book;
import com.foo.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
public class HelloController {
	@Autowired
	private BookService bookService;

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

	@PostMapping("/add")
	public long add(
		@RequestBody Book book
	) {
		return bookService.add(BookDAO.from(book));
	}
}