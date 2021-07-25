package com.ss.lms.presentation;

import java.sql.SQLException;

/**
 * Library Management System - July 26, 2021
 * 
 * Description: Create a library management application on the Command Line.
 * 
 * JUnit tests are located in: tests/com/ss/lms/tests/LMSTest.java
 * 
 * @author Tim Ning
 *
 */
public class LMS {

	// start the program
	public static void main(String[] args) {
		try {
			new MenuManager();
		} catch (SQLException e) {
			// all thrown exceptions which are not caught elsewhere will end up here
			e.printStackTrace();
		}
	}

}
