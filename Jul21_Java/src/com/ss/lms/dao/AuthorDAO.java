package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domain.Author;

/*
 * Handles data from the author & book_authors tables
 */
public class AuthorDAO extends BaseDAO<Author> {

	public AuthorDAO(Connection conn) {
		super(conn);
	}

	// ---- book_authors table ----

	public void addAuthorByBookId(int authorId, int bookId) throws SQLException {
		save("INSERT INTO tbl_book_authors VALUES (?, ?)", new Object[] { bookId, authorId });
	}

	public void deleteAuthorByBookId(int authorId, int bookId) throws SQLException {
		save("DELETE FROM tbl_book_authors WHERE authorId = ? AND bookId = ?", new Object[] { authorId, bookId });
	}

	public List<Author> readAuthorsByBookId(int bookId) throws SQLException {
		return read("SELECT * FROM tbl_book_authors ba INNER JOIN tbl_author a ON ba.authorId = a.authorId "
				+ "WHERE bookId = ?", new Object[] { bookId });
	}

	// ---- author table ----

	public void addAuthor(int id, String name) throws SQLException {
		save("INSERT INTO tbl_author VALUES (?, ?)", new Object[] { id, name });
	}

	public void updateAuthor(int id, String name) throws SQLException {
		save("UPDATE tbl_author SET authorName = ? WHERE authorId = ?", new Object[] { name, id });
	}

	public void deleteAuthor(int id) throws SQLException {
		save("DELETE FROM tbl_author WHERE authorId = ?", new Object[] { id });
	}

	public Author readAuthorById(int authorId) throws SQLException {
		List<Author> list = read("SELECT * FROM tbl_author WHERE authorId = ?", new Object[] { authorId });
		return (list.size() > 0) ? list.get(0) : null;
	}

	public List<Author> readAllAuthors() throws SQLException {
		return read("SELECT * FROM tbl_author", null);
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> list = new ArrayList<>();
		while (rs.next()) {
			list.add(new Author(rs.getInt("authorId"), rs.getString("authorName")));
		}
		return list;
	}

}
