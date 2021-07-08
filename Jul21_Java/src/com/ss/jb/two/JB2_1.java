package com.ss.jb.two;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Java Basics 2 - Assignment 1
 * 
 * Sums multiple values entered into the console.
 * 
 * @author Tim Ning
 *
 */
public class JB2_1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Double sum = 0d;

		// input prompt
		System.out.println("This program sums multiple inputs together & displays the running total.");
		System.out.println("Type x to exit." + System.lineSeparator());

		// main program loop
		while (true) {
			System.out.println("Enter a number:");

			try {
				// standard input via console
				String s = br.readLine();

				// exit condition
				if ("x".equals(s))
					break;

				// add to running total
				sum += Double.parseDouble(s);
			} catch (Exception ex) {
				// input was not a number
				System.out.println("Input must be a number!");
				continue;
			}

			System.out.println("Running total = " + sum);
		}

		// end of program
		System.out.println("Program exited!");
	}
}
