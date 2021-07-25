package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.lms.dao.BookDAO;
import com.ss.lms.dao.BookCopiesDAO;
import com.ss.lms.dao.BookLoanDAO;
import com.ss.lms.dao.BorrowerDAO;
import com.ss.lms.dao.BranchDAO;
import com.ss.lms.domain.Book;
import com.ss.lms.domain.BookCopies;
import com.ss.lms.domain.BookLoan;
import com.ss.lms.domain.Borrower;
import com.ss.lms.domain.Branch;

/*
 * Contains methods used by more than 1 service type
 */
public abstract class BaseService {

	protected ConnectionUtil connUtil = new ConnectionUtil();
	protected final String endl = System.lineSeparator();

	// ---- book loan ----

	public List<BookLoan> readAllLoans() throws SQLException {
		Connection conn = connUtil.getConnection();
		BookLoanDAO dao = new BookLoanDAO(conn);
		List<BookLoan> list = dao.readAllLoans();
		conn.close();
		return list;
	}

	public List<BookLoan> readLoansByCardNo(int cardNo, boolean includeDateIn) throws SQLException {
		Connection conn = connUtil.getConnection();
		BookLoanDAO dao = new BookLoanDAO(conn);
		List<BookLoan> list = dao.readLoansByCardNo(cardNo, includeDateIn);
		conn.close();
		return list;
	}

	// ---- branch ----

	public Branch readBranchById(int id) throws SQLException {
		Connection conn = connUtil.getConnection();
		BranchDAO brDao = new BranchDAO(conn);
		Branch b = brDao.readBranchById(id);
		conn.close();
		return b;
	}

	public void updateBranch(int id, String name, String address) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BranchDAO dao = new BranchDAO(conn);
			if (!name.isBlank())
				dao.updateBranch(id, "branchName", name);
			if (!address.isBlank())
				dao.updateBranch(id, "branchAddress", address);
			conn.commit();
			printSuccess("Branch updated");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public List<Branch> readAllBranches() throws SQLException {
		Connection conn = connUtil.getConnection();
		BranchDAO brDao = new BranchDAO(conn);
		List<Branch> list = brDao.readAllBranches();
		conn.close();
		return list;
	}

	// ---- book ----

	public Book readBookById(int id) throws SQLException {
		Connection conn = connUtil.getConnection();
		BookDAO dao = new BookDAO(conn);
		Book b = dao.readBookById(id);
		conn.close();
		return b;
	}

	public List<Book> readAllBooks() throws SQLException {
		Connection conn = connUtil.getConnection();
		BookDAO dao = new BookDAO(conn);
		List<Book> list = dao.readAllBooks();
		conn.close();
		return list;
	}

	// ---- book copies ----

	public BookCopies readCopiesByIds(int bookId, int branchId) throws SQLException {
		Connection conn = connUtil.getConnection();
		BookCopiesDAO dao = new BookCopiesDAO(conn);
		BookCopies bc = dao.readCopiesByIds(bookId, branchId);
		conn.close();
		return bc;
	}

	// ---- borrower ----

	public Borrower readBorrowerByCardNo(int cardNo) throws SQLException {
		Connection conn = connUtil.getConnection();
		BorrowerDAO bDao = new BorrowerDAO(conn);
		Borrower b = bDao.readBorrowerByCardNo(cardNo);
		conn.close();
		return b;
	}

	// ---- helper methods ----

	// printed after any add/update/delete operation
	protected void printSuccess(String s) {
		System.out.println(s + " successfully!" + endl);
	}

	// an add/update/delete operation failed to write to the database
	protected void printFailed(SQLException e) {
		System.out.println("Operation failed: " + e.getMessage() + endl);
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
}
