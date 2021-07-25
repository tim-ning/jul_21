package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/*
 * Superclass contains methods used by all DAO subclasses.
 */
public abstract class BaseDAO<T> {

	public static Connection conn = null;

	public BaseDAO(Connection conn) {
		this.conn = conn;
	}

	// saves a row & returns nothing
	public void save(String sql, Object[] vals) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if (vals != null) {
			int ct = 1;
			for (Object o : vals) {
				pstmt.setObject(ct, o);
				ct++;
			}
		}
		pstmt.executeUpdate();
	}

	// saves a row & returns the primary key
	public Integer saveWithPk(String sql, Object[] vals) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		if (vals != null) {
			int ct = 1;
			for (Object o : vals) {
				pstmt.setObject(ct, o);
				ct++;
			}
		}
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();

		if (rs.next()) {
			return rs.getInt(1);
		}
		return null;
	}

	// reads multiple rows from db
	public List<T> read(String sql, Object[] vals) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if (vals != null) {
			int ct = 1;
			for (Object o : vals) {
				pstmt.setObject(ct, o);
				ct++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		return extractData(rs);
	}

	// create a list of domain objects from the resultset rows
	public abstract List<T> extractData(ResultSet rs) throws SQLException;

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
	 */
}