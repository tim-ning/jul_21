package com.ss.jb.four;

/**
 * Java Basics 4 - Assignment 1
 * 
 * Implement a Singleton with double checked locking.
 * 
 * @author Tim Ning
 *
 */
public class JB4_1 {

	private static volatile JB4_1 instance = null;

	// constructor
	private JB4_1() {
		System.out.println("Singleton has been created.");
	}

	// returns the only instance of this class
	public static JB4_1 getInstance() {

		// create the instance if it doesn't already exist
		if (instance == null) {
			synchronized (JB4_1.class) {
				//check again that it's still null
				if (instance == null)
					instance = new JB4_1();
			}
		}
		return instance;
	}

	/**
	 * Start program & create the singleton.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JB4_1.getInstance();
	}

}
