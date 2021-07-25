package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domain.BookLoan;

/*
 * assume that we can uniquely identify a loan using the combination of: bookId + branchId + cardNo.
 * the borrower's checkout() function will disallow adding a duplicate entry.
 */
public class BookLoanDAO extends BaseDAO<BookLoan> {

	public BookLoanDAO(Connection conn) {
		super(conn);
	}

	public void createLoan(int bookId, int branchId, int cardNo, String dateOut, String dueDate) throws SQLException {
		save("INSERT INTO tbl_book_loans VALUES (?, ?, ?, ?, ?, NULL)",
				new Object[] { bookId, branchId, cardNo, dateOut, dueDate });
	}

	// admin overrides the due date for a loan
	public void updateDueDate(int bookId, int branchId, int cardNo, String dueDate) throws SQLException {
		save("UPDATE tbl_book_loans SET dueDate = ? WHERE bookId = ? AND branchId = ? AND cardNo = ?",
				new Object[] { dueDate, bookId, branchId, cardNo });
	}

	// on a book return, add a value to dateIn
	public void updateDateIn(int bookId, int branchId, int cardNo, String dateIn) throws SQLException {
		save("UPDATE tbl_book_loans SET dateIn = ? WHERE bookId = ? AND branchId = ? AND cardNo = ?",
				new Object[] { dateIn, bookId, branchId, cardNo });
	}

	// if checking out the same book again,
	// update dateOut & dueDate, and blank dateIn
	public void updateDateOut(int bookId, int branchId, int cardNo, String dateOut, String dueDate)
			throws SQLException {
		save("UPDATE tbl_book_loans SET dateOut = ?, dueDate = ?, dateIn = NULL "
				+ "WHERE bookId = ? AND branchId = ? AND cardNo = ?",
				new Object[] { dateOut, dueDate, bookId, branchId, cardNo });
	}

	public List<BookLoan> readAllLoans() throws SQLException {
		return read("SELECT * FROM tbl_book_loans", null);
	}

	// can toggle whether to include rows where the loan has been returned
	public List<BookLoan> readLoansByCardNo(int cardNo, boolean includeDateIn) throws SQLException {
		if (includeDateIn)
			return read("SELECT * FROM tbl_book_loans WHERE cardNo = ?", new Object[] { cardNo });
		else
			return read("SELECT * FROM tbl_book_loans WHERE cardNo = ? AND dateIn IS NULL", new Object[] { cardNo });
	}

	public List<BookLoan> extractData(ResultSet rs) throws SQLException {
		List<BookLoan> list = new ArrayList<>();
		while (rs.next()) {
			list.add(new BookLoan(rs.getInt("bookId"), rs.getInt("branchId"), rs.getInt("cardNo"),
					rs.getString("dateOut"), rs.getString("dueDate"), rs.getString("dateIn")));
		}
		return list;
	}

}