package com.foo.library.service;

import com.foo.library.db.BookDAO;
import com.foo.library.model.Book;
import com.foo.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Component
public class BookService {
	@Autowired
	private BookRepository bookRepository;

	public List<Book> list() {
		return list(null, null);
	}

	public List<Book> list(String author, String genre) {
		List<BookDAO> all_books = bookRepository.findAll();
		Stream<BookDAO> books = all_books.stream();
		if (author != null) {
			books = books.filter(book -> author.equals(book.getAuthor()));
		}
		if (genre != null) {
			books = books.filter(book -> genre.equals(book.getGenre()));
		}
		return books.map(Book::from).collect(Collectors.toList());
	}

	public long add(Book book) {
		return bookRepository.save(BookDAO.from(book)).getId();
	}

	public Book get(long id) {
		for (Book book : list()) {
			if (book.getId() == id) {
				return book;
			}
		}
		throw new IllegalArgumentException(String.format("no book with id: %s", id));
	}
}
