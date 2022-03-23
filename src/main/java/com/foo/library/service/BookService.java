package com.foo.library.service;

import com.foo.library.db.BookDAO;
import com.foo.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class BookService {
	@Autowired
	private BookRepository bookRepository;

	public List<BookDAO> list() {
		return bookRepository.findAll();
	}

	public long add(BookDAO book) {
		return bookRepository.save(book).getId();
	}
}
