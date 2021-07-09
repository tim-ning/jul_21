package com.ss.jb.three;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Java Basics 3 - Assignment 3
 * 
 * Counts the number of occurrences of a given character within a file.
 * 
 * @author Tim Ning
 *
 */
public class JB3_3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// set the file to read (same file that was generated in assignment #2)
		String filename = "test.txt";

		String pkg = ".src.com.ss.jb.three.";
		String path = System.getProperty("user.dir") + pkg.replace('.', File.separatorChar) + filename;

		System.out.println("File to scan = " + path + System.lineSeparator());

		// read all chars into a byte array
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			// file read failed. print exception message & exit program.
			e.printStackTrace();
			System.out.println("Program exited.");
			return;
		}

		// input prompt
		System.out.println("Type the character to count the occurrences of:");

		String s = null;

		// read standard input via console
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

			// verify input. allow re-tries.
			while (true) {
				s = br.readLine();
				if (s.length() == 1)
					break;
				else
					System.out.println("Input must be 1 character! Try again:");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}

		char c = s.charAt(0);
		int counter = 0;

		// count occurrences of character
		for (byte b : bytes) {
			if (c == b)
				counter++;
		}

		// print the result
		System.out.println("The character '" + c + "' occurs " + counter + " times.");
	}
}
