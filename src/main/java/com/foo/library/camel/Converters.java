package com.foo.library.camel;

import com.foo.library.model.Book;
import com.foo.library.model.Books;
import org.apache.camel.Converter;

@Converter
public class Converters {
	@Converter
	public static Books toBooks(Book[] books) {
		return new Books(books);
	}
}
