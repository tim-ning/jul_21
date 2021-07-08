package com.ss.jb.one;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Java Basics 1 - Assignment 2
 * 
 * User has 5 attempts to guess within +/-10 of a randomly generated number.
 * 
 * @author Tim Ning
 *
 */
public class Numbers {
	/**
	 * Start the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Random rand = new Random();
		boolean success = false;

		// generate a random number from 1-100 inclusive
		int num = rand.nextInt(100) + 1;

		// initialize outside of the guess range
		int guess = Integer.MIN_VALUE;

		System.out.println("Guess a number from 1-100:");

		// user attempts 5 guesses
		for (int i = 4; i >= 0; i--) {
			// standard input via console
			try {
				guess = Integer.parseInt(br.readLine());
			} catch (Exception ex) {
				// input was not an integer
				System.out.println("Invalid input!");
			}

			// determine whether guess is within 10 of the random number
			if (Math.abs(num - guess) <= 10) {
				success = true;
				break;
			} else if (i > 0) {
				System.out.println("Try again. " + i + " attempt" + (i == 1 ? "" : "s") + " remaining:");
			}
		}

		// print the outcome
		System.out.print(success ? "Success!" : "Sorry!");
		System.out.println(" The number was " + num + ".");
	}

}
