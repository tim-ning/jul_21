package com.ss.jb.one;

/**
 * Java Basics 1 - Assignment 1
 * 
 * Prints patterns to the console.
 * 
 * @author Tim Ning
 *
 */
public class Patterns {
	// constructor
	public Patterns() {
		// print each of the 4 sections
		for (int i = 1; i <= 4; i++) {
			// print number, with even numbers surrounded by dots
			printDots(i, 7 + i);
			System.out.println(i + ")");
			printDots(i, 8 + i);

			// print 4 rows of stars & spaces
			for (int j = 0; j < 4; j++)
				printStars(i, j);
		}
	}

	// print 1 row of stars & spaces
	private void printStars(int num, int i) {
		// section number
		switch (num) {
		case 1:
			printChar('*', i + 1);
			break;

		case 2:
			printChar('*', 4 - i);
			break;

		case 3:
			printChar(' ', 5 - i);
			printChar('*', i * 2 + 1);
			break;

		case 4:
			printChar(' ', i + 2);
			printChar('*', 7 - i * 2);
			break;
		}

		System.out.println();
	}

	// repeatedly print a char, n times
	private void printChar(char c, int n) {
		for (int i = 0; i < n; i++)
			System.out.print(c);
	}

	// print 1 row of dots
	private void printDots(int num, int i) {
		// ignore odd numbers
		if (num % 2 != 0)
			return;

		printChar('.', i);
		System.out.println();
	}

	/**
	 * Start the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Patterns();
	}
}
