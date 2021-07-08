package com.ss.jb.two;

import java.util.Random;

/**
 * Java Basics 2 - Assignment 3
 * 
 * Calculate the area of a rectangle, circle, & triangle.
 * 
 * @author Tim Ning
 *
 */
public class JB2_3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// set scale of shapes
		int scale = 100;
		Random rand = new Random();

		// randomly generate shape dimensions
		Rectangle rect = new Rectangle(rand.nextInt(scale), rand.nextInt(scale));
		Circle circ = new Circle(rand.nextInt(scale));
		Triangle tri = new Triangle(rand.nextInt(scale), rand.nextInt(scale));

		System.out.println("Randomly generated some shapes:" + System.lineSeparator());

		// calculate area & print to console
		rect.display();
		circ.display();
		tri.display();
	}
}
