package com.foo.library.service;

import com.foo.library.db.BookDAO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;


@SpringBootTest
public class BookServiceTest {

	@Autowired
	private BookService bookService;

	@Test
	public void isEmpty() throws Exception {
		Matchers.empty().matches(bookService.list());
	}

	@Test
	public void canAdd() throws Exception {
		BookDAO book = BookDAO.of(-1, "t", "g", "a");
		long id = bookService.add(book);
		Matchers.equalTo(1).matches(id);
		Matchers.equalTo(
			Arrays.asList(book)
		).matches(bookService.list());
	}
}
