package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.lms.dao.BookDAO;
import com.ss.lms.dao.BookCopiesDAO;
import com.ss.lms.dao.BookLoanDAO;
import com.ss.lms.dao.BranchDAO;
import com.ss.lms.domain.Book;
import com.ss.lms.domain.Branch;

public class BorrowerService extends BaseService {

	// subtract a book copy & create a book loan
	public void createLoan(int bookId, int branchId, int cardNo, String dateOut, String dueDate, int loanState)
			throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookCopiesDAO cDao = new BookCopiesDAO(conn);
			BookLoanDAO lDao = new BookLoanDAO(conn);
			cDao.incrementCopy(bookId, branchId, -1);
			if (loanState == 2) // the row exists, update dateOut & blank dateIn
				lDao.updateDateOut(bookId, branchId, cardNo, dateOut, dueDate);
			else // the row doesn't exist, so create it
				lDao.createLoan(bookId, branchId, cardNo, dateOut, dueDate);
			conn.commit();
			printSuccess("Book checked out");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	// add a book copy & set dateIn on the loan
	public void returnLoan(int bookId, int branchId, int cardNo, String dateIn, boolean hasCopies) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookCopiesDAO cDao = new BookCopiesDAO(conn);
			BookLoanDAO lDao = new BookLoanDAO(conn);
			if (hasCopies)
				cDao.incrementCopy(bookId, branchId, 1);
			else
				cDao.createCopies(bookId, branchId, 1);
			lDao.updateDateIn(bookId, branchId, cardNo, dateIn);
			conn.commit();
			printSuccess("Book returned");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	// get a list of branches where this borrower has books checked out
	public List<Branch> readBranchesByCardNo(int cardNo) throws SQLException {
		Connection conn = connUtil.getConnection();
		BranchDAO brDao = new BranchDAO(conn);
		List<Branch> list = brDao.readBranchesByCardNo(cardNo);
		conn.close();
		return list;
	}

	// get list of books available to checkout for a branch
	public List<Book> readBooksByBranch(int branchId) throws SQLException {
		Connection conn = connUtil.getConnection();
		BookDAO dao = new BookDAO(conn);
		List<Book> list = dao.readBooksByBranch(branchId);
		conn.close();
		return list;
	}

	// list of checkouts. does not include rows where dateIn exists
	public List<Book> readBooksByLoan(int branchId, int cardNo) throws SQLException {
		Connection conn = connUtil.getConnection();
		BookDAO dao = new BookDAO(conn);
		List<Book> list = dao.readBooksByLoan(branchId, cardNo);
		conn.close();
		return list;
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
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
}
