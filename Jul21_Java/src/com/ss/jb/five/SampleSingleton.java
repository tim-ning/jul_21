package com.ss.jb.five;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Java Basics 5 - Assignment 6
 * 
 * Fix the given singleton class.
 * 
 * @author Tim Ning
 *
 */
public class SampleSingleton {

	private static Connection conn = null;

	private static volatile SampleSingleton instance = null;

	// constructor
	private SampleSingleton() {
		// System.out.println("Singleton has been created.");
	}

	// returns the only instance of this class
	public static SampleSingleton getInstance() {

		// create the instance if it doesn't already exist
		if (instance == null) {
			synchronized (SampleSingleton.class) {
				// check again that it's still null
				if (instance == null)
					instance = new SampleSingleton();
			}
		}
		return instance;
	}

}
