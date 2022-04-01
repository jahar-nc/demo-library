package com.foo.library.service;

import com.foo.library.db.BookDAO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

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
		assertThat(1).isEqualTo(id);
		assertThat(singletonList(BookDAO.of(1, "t", "g", "a"))).isEqualTo(bookService.list());
	}
}
