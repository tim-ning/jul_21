package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domain.BookCopies;

public class BookCopiesDAO extends BaseDAO<BookCopies> {

	public BookCopiesDAO(Connection conn) {
		super(conn);
	}

	public void createCopies(int bookId, int branchId, int copies) throws SQLException {
		save("INSERT INTO tbl_book_copies VALUES (?, ?, ?)", new Object[] { bookId, branchId, copies });
	}

	public void updateCopies(int bookId, int branchId, int copies) throws SQLException {
		save("UPDATE tbl_book_copies SET noOfCopies = ? WHERE bookId = ? AND branchId = ?",
				new Object[] { copies, bookId, branchId });
	}

	public void incrementCopy(int bookId, int branchId, int increment) throws SQLException {
		save("UPDATE tbl_book_copies SET noOfCopies = noOfCopies + ? WHERE bookId = ? AND branchId = ?",
				new Object[] { increment, bookId, branchId });
	}

	public BookCopies readCopiesByIds(int bookId, int branchId) throws SQLException {
		List<BookCopies> list = read(
				"SELECT * FROM tbl_book_copies bc INNER JOIN tbl_library_branch lb ON bc.branchId = lb.branchId "
						+ "INNER JOIN tbl_book b ON b.bookId = bc.bookId WHERE b.bookId = ? AND lb.branchId = ?",
				new Integer[] { bookId, branchId });
		return (list.size() > 0) ? list.get(0) : null;
	}

	public List<BookCopies> extractData(ResultSet rs) throws SQLException {
		List<BookCopies> list = new ArrayList<>();
		while (rs.next()) {
			list.add(new BookCopies(rs.getInt("bookId"), rs.getInt("branchId"), rs.getInt("noOfCopies")));
		}
		return list;
	}

}