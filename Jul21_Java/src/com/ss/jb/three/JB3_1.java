package com.ss.jb.three;

import java.io.File;

/**
 * Java Basics 3 - Assignment 1
 * 
 * Prints the file/directory tree of a given directory.
 * 
 * @author Tim Ning
 *
 */
public class JB3_1 {

	// constructor
	public JB3_1() {
		// set the source directory (use current working dir as example)
		String sourceDir = System.getProperty("user.dir");

		System.out.println("Listing all files & directories in " + sourceDir + System.lineSeparator());

		// start the scan
		try {
			scan(new File(sourceDir), 0);
		} catch (Exception e) {
			// all exceptions are thrown here
			e.printStackTrace();
		}
	}

	// recursively scan a directory & print the files
	public void scan(File dir, int level) throws Exception {
		for (File f : dir.listFiles()) {

			// add indentation to show tree level
			for (int i = 0; i < level; i++)
				System.out.print("    ");

			if (f.isDirectory()) {
				System.out.println(f.getName() + " <DIR>");
				scan(f, level + 1);
			} else {
				System.out.println(f.getName());
			}
		}
	}

	/**
	 * Start the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new JB3_1();
	}
}
