package com.foo.library.model;

import com.foo.library.db.BookDAO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(staticName="of")
@XmlRootElement(name = "book", namespace = "")
public class Book implements Cloneable {
	private long id;

	private String title;

	private String genre;

	private String author;

	public static Book from(BookDAO book) {
		return new Book(
			book.getId(),
			book.getTitle(),
			book.getGenre(),
			book.getAuthor()
		);
	}

	@Override
	public Book clone() {
		try {
			Book clone = (Book) super.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}
