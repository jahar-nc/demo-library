package com.foo.library.model;

import org.junit.jupiter.api.Test;

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
}
