package com.ss.jb.three;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Java Basics 3 - Assignment 2
 * 
 * Appends randomly generated text to a file.
 * 
 * @author Tim Ning
 *
 */
public class JB3_2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// set the file to write
		String filename = "test.txt";

		String pkg = ".src.com.ss.jb.three.";
		String path = System.getProperty("user.dir") + pkg.replace('.', File.separatorChar) + filename;

		Random rand = new Random();

		System.out.println("Appending the following text to file " + path + System.lineSeparator());

		// set append mode
		try (BufferedWriter br = new BufferedWriter(new FileWriter(path, true))) {

			// generate 100 random ascii characters in the range of 32-126
			for (int i = 0; i < 100; i++) {
				int c = rand.nextInt(94) + 32;
				br.write(c);
				System.out.print((char) c);
			}
			br.write(System.lineSeparator());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// program finished
		System.out.println(System.lineSeparator() + System.lineSeparator() + "Done!");
	}
}
