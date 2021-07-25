package com.ss.lms.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Connects to the MySQL database.
 * 1 connection is made per transaction.
 */
public class ConnectionUtil {

	private final String url = "jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC";
	private final String user = "root";
	private final String pass = "mdCJMpC-4WE5yg";

	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, pass);
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println("Error: Could not connect to database.");
			e.printStackTrace();
		}
		return conn;
	}

}
