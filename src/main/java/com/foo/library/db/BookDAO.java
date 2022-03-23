package com.foo.library.db;

import com.foo.library.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="of")
@Entity
public class BookDAO {
	@Id
	@GeneratedValue
	public long id;

	public String title;

	public String genre;

	public String author;

	public static BookDAO from(Book book) {
		return BookDAO.of(
			book.getId(),
			book.getTitle(),
			book.getGenre(),
			book.getAuthor()
		);
	}
}
