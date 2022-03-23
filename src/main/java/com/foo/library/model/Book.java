package com.foo.library.model;

import com.foo.library.db.BookDAO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(staticName="of")
public class Book {
	public long id;

	public String title;

	public String genre;

	public String author;

	public static Book from(BookDAO book) {
		return new Book(
			book.getId(),
			book.getTitle(),
			book.getGenre(),
			book.getTitle()
		);
	}
}
