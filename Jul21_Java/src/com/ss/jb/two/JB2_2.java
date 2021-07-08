package com.ss.jb.two;

import java.util.Random;

/**
 * Java Basics 2 - Assignment 2
 * 
 * Find the largest number in a 2D array.
 * 
 * @author Tim Ning
 *
 */
public class JB2_2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Random rand = new Random();

		// set the size of the array
		byte[][] arr = new byte[4][4];

		// initialize the array with random bytes, ranging from -128 to +127.
		for (int i = 0; i < arr.length; i++)
			rand.nextBytes(arr[i]);

		System.out.println("Filled a " + arr.length + "x" + arr[0].length + " array with random bytes:");

		byte max = Byte.MIN_VALUE;
		int row = 0;
		int col = 0;

		// search through the array
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				// show the values in the console
				System.out.print(" [" + String.format("%1$4s", arr[i][j]) + "]");

				// find the position of the largest number
				if (arr[i][j] > max) {
					max = arr[i][j];
					row = i;
					col = j;
				}
			}
			System.out.println();
		}

		// print the max value
		System.out.println(
				System.lineSeparator() + "The largest number is " + max + " at row " + row + ", column " + col + ".");

	}

}
