package com.ss.jb.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import com.ss.jb.four.Line;

/**
 * Java Basics 4 - Assignment 4
 * 
 * Unit tests for the Line Class (located in package com.ss.jb.four)
 * 
 * @author Tim Ning
 *
 */
public class LineTest {

	// very small value for comparing doubles
	private double delta = 0.0001;

	Line ln1 = new Line(0, 0, 1, 1);
	Line ln2 = new Line(2, 0, 3, 1);
	Line ln3 = new Line(4, 7, 2, 10);
	Line ln4 = new Line(0, 6, 0, 7);

	@Test
	public void testParallelTo() {
		assertEquals(true, ln1.parallelTo(ln2));
		assertEquals(false, ln1.parallelTo(ln3));
		assertNotEquals(false, ln1.parallelTo(ln2));
	}

	@Test
	public void testSlope() {
		assertEquals(new Double(1), ln1.getSlope(), delta);
		assertNotEquals(new Double(9), ln1.getSlope(), delta);
		assertThrows(ArithmeticException.class, ln4::getSlope);
	}

	@Test
	public void testDistance() {
		assertEquals(new Double(Math.sqrt(2)), ln1.getDistance(), delta);
		assertNotEquals(new Double(0), ln1.getDistance(), delta);
	}

}
