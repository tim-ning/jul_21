package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domain.Book;

/*
 * Retrieve data from both book & author tables, & store in book object.
 */
public class BookDAO extends BaseDAO<Book> {

	public BookDAO(Connection conn) {
		super(conn);
	}

	public void addBook(int id, String title, int authId, int pubId) throws SQLException {
		save("INSERT INTO tbl_book VALUES (?, ?, ?, ?)", new Object[] { id, title, authId, pubId });
	}

	public void updateBook(int bookId, String title, int authId, int pubId) throws SQLException {
		save("UPDATE tbl_book SET title = ?, authId = ?, pubId = ? WHERE bookId = ?",
				new Object[] { title, authId, pubId, bookId });
	}

	public void deleteBook(int id) throws SQLException {
		save("DELETE FROM tbl_book WHERE bookId = ?", new Object[] { id });
	}

	public Book readBookById(int bookId) throws SQLException {
		List<Book> list = read(
				"SELECT * FROM tbl_book b INNER JOIN tbl_author a ON b.authId = a.authorId WHERE bookId = ?",
				new Object[] { bookId });
		return (list.size() > 0) ? list.get(0) : null;
	}

	public List<Book> readAllBooks() throws SQLException {
		return read("SELECT * FROM tbl_book b INNER JOIN tbl_author a ON b.authId = a.authorId ORDER BY bookId", null);
	}

	// list for borrower checkout menu
	public List<Book> readBooksByBranch(int branchId) throws SQLException {
		return read("SELECT * FROM tbl_book_copies bc INNER JOIN tbl_library_branch lb ON bc.branchId = lb.branchId "
				+ "INNER JOIN tbl_book b ON b.bookId = bc.bookId INNER JOIN tbl_author a ON b.authId = a.authorId "
				+ "WHERE lb.branchId = ? AND noOfCopies > 0 ORDER BY b.bookId", new Integer[] { branchId });
	}

	// list for borrower return menu. does not include rows where dateIn exists.
	public List<Book> readBooksByLoan(int branchId, int cardNo) throws SQLException {
		return read(
				"SELECT * FROM tbl_book b INNER JOIN tbl_book_loans bl ON b.bookId = bl.bookId "
						+ "INNER JOIN tbl_author a ON b.authId = a.authorId "
						+ "WHERE branchId = ? AND cardNo = ? AND dateIn IS NULL ORDER BY b.bookId",
				new Integer[] { branchId, cardNo });
	}

	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> list = new ArrayList<>();
		while (rs.next()) {
			list.add(new Book(rs.getInt("bookId"), rs.getInt("authId"), rs.getInt("pubId"), rs.getString("title"),
					rs.getString("authorName")));
		}
		return list;
	}

}