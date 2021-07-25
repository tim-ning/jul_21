package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domain.Genre;

/*
 * Handles data from the genre & book_genres tables
 */
public class GenreDAO extends BaseDAO<Genre> {

	public GenreDAO(Connection conn) {
		super(conn);
	}

	// ---- book_genres table ----

	// bookId must exist in the book table
	public void addGenreByBookId(int genreId, int bookId) throws SQLException {
		save("INSERT INTO tbl_book_genres VALUES (?, ?)", new Object[] { genreId, bookId });
	}

	public void deleteGenreByBookId(int genreId, int bookId) throws SQLException {
		save("DELETE FROM tbl_book_genres WHERE genre_id = ? AND bookId = ?", new Object[] { genreId, bookId });
	}

	public List<Genre> readGenresByBookId(int bookId) throws SQLException {
		return read(
				"SELECT * FROM tbl_book_genres bg INNER JOIN tbl_genre g ON bg.genre_id = g.genre_id WHERE bookId = ?",
				new Object[] { bookId });
	}

	// ---- genre table ----

	public void addGenre(int id, String name) throws SQLException {
		save("INSERT INTO tbl_genre VALUES (?, ?)", new Object[] { id, name });
	}

	public void updateGenre(int id, String name) throws SQLException {
		save("UPDATE tbl_genre SET genre_name = ? WHERE genre_id = ?", new Object[] { name, id });
	}

	public void deleteGenre(int id) throws SQLException {
		save("DELETE FROM tbl_genre WHERE genre_id = ?", new Object[] { id });
	}

	public Genre readGenreById(int genreId) throws SQLException {
		List<Genre> list = read("SELECT * FROM tbl_genre WHERE genre_id = ?", new Object[] { genreId });
		return (list.size() > 0) ? list.get(0) : null;
	}

	public List<Genre> readAllGenres() throws SQLException {
		return read("SELECT * FROM tbl_genre", null);
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> list = new ArrayList<>();
		while (rs.next()) {
			list.add(new Genre(rs.getInt("genre_id"), rs.getString("genre_name")));
		}
		return list;
	}

}
