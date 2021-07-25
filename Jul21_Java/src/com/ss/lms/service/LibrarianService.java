package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.ss.lms.dao.BookCopiesDAO;

public class LibrarianService extends BaseService {

	public void createCopies(int bookId, int branchId, int copies) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookCopiesDAO dao = new BookCopiesDAO(conn);
			dao.createCopies(bookId, branchId, copies);
			conn.commit();
			printSuccess("Copies updated");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void updateCopies(int bookId, int branchId, int copies) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookCopiesDAO dao = new BookCopiesDAO(conn);
			dao.updateCopies(bookId, branchId, copies);
			conn.commit();
			printSuccess("Copies updated");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

}
