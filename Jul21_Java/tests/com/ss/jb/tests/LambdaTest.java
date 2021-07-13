
package com.ss.jb.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.time.Month;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import org.junit.Test;
import com.ss.jb.five.JB5;
import com.ss.jb.five.SampleSingleton;

/**
 * Java Basics 5 - JUnit tests for all assignments.
 * 
 * Methods are located in package com.ss.jb.five
 * 
 * @author Tim Ning
 *
 */
public class LambdaTest {

	JB5 jb5 = new JB5(); // class containing methods for all JB5 assignments
	String[] arr = { "lorem", "ipsum", "dolor", "sit", "amet" };

	// Lambdas and functional interfaces and streams

	// 1. Basic lambdas. Make an array containing a few Strings. Sort it by:

	// length (i.e., shortest to longest)
	@Test
	public void shortestTest() {
		assertThat(new String[] { "sit", "amet", "lorem", "ipsum", "dolor" }, is(jb5.shortestToLongest(arr)));
	}

	// reverse length (i.e., longest to shortest)
	@Test
	public void longestTest() {
		assertThat(new String[] { "lorem", "ipsum", "dolor", "amet", "sit" }, is(jb5.longestToShortest(arr)));
	}

	// alphabetically by the first character only
	@Test
	public void alphabeticalTest() {
		assertThat(new String[] { "amet", "dolor", "ipsum", "lorem", "sit" }, is(jb5.alphabetical(arr)));
	}

	// Strings that contain 'e' first, everything else second.
	@Test
	public void e1Test() {
		assertThat(new String[] { "lorem", "amet", "ipsum", "dolor", "sit" }, is(jb5.eFirst1(arr)));
	}

	// Redo the previous problem, but use a static helper method
	@Test
	public void e2Test() {
		assertThat(new String[] { "lorem", "amet", "ipsum", "dolor", "sit" }, is(jb5.eFirst2(arr)));
	}

	// 2. Using Java 8 features write a method that returns a comma separated string
	// based on a given list of integers. Each element should be preceded by the
	// letter 'e' if the number is even, and preceded by the letter 'o' if the
	// number is odd.
	@Test
	public void evenOddTest() {
		assertEquals("o3, e44, e0, o1, e2, o3", jb5.evenOdd(Arrays.asList(3, 44, 0, 1, 2, 3)));
	}

	// 3. Given a list of Strings, write a method that returns a list of all strings
	// that start with the letter 'a' (lower case) and have exactly 3 letters.
	@Test
	public void a3WordTest() {
		List<String> a = Arrays.asList("aaa", "abc", "a", "and", "apple", "foo", "");
		assertThat(Arrays.asList("aaa", "abc", "and"), is(jb5.a3Words(a)));
	}

	// -------------------------

	// Date-Time API questions:

	// 1. Which class would you use to store your birthday in years, months, days,
	// seconds, and nanoseconds?
	@Test
	public void testBirthday() {
		assertEquals("1914-07-28T12:05:40.987654321Z", jb5.birthday("1914-07-28", "12:05:40", "987654321"));
	}

	// 2. Given a random date, how would you find the date of the previous Thursday?
	@Test
	public void testThursday() {
		assertEquals("2021-07-08", jb5.previousThursday("2021-07-12"));
	}

	// 3. What is the difference between a ZoneId and a ZoneOffset?
	@Test
	public void testZoneIdVsOffset() {
		assertEquals(ZoneId.of("Europe/Berlin"), jb5.getZoneId());
		assertEquals(ZoneOffset.of("+02:00"), jb5.getZoneOffset(LocalDateTime.parse("2021-07-12T12:12:12")));
	}

	// 4. How would you convert an Instant to a ZonedDateTime? How would you convert
	// a ZonedDateTime to an Instant?
	@Test
	public void testZonedDateTimeInstant() {
		ZonedDateTime z = jb5.convertToZonedDateTime(Instant.parse("2021-07-12T12:12:12.001Z"));
		assertEquals("2021-07-12T14:12:12.001+02:00[Europe/Berlin]", z.toString());
		assertEquals("2021-07-12T12:12:12.001Z", jb5.convertToInstant(z).toString());
	}

	// 5. Write an example that, for a given year, reports the length of each month
	// within that year.
	@Test
	public void testMonthLengths() {
		List<Integer> answer = Arrays.asList(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
		assertThat(answer, is(jb5.monthLengths(1932)));
	}

	// 6. Write an example that, for a given month of the current year, lists all of
	// the Mondays in that month.
	@Test
	public void testMondays() {
		List<String> answer = Arrays.asList("2021-07-05", "2021-07-12", "2021-07-19", "2021-07-26");
		assertThat(answer, is(jb5.allMondays(Month.JULY)));
	}

	// 7. Write an example that tests whether a given date occurs on Friday the
	// 13th.
	@Test
	public void testFriday13() {
		assertEquals(true, jb5.friday13("2020-11-13"));
		assertEquals(false, jb5.friday13("1901-01-01"));
		assertEquals(false, jb5.friday13("2021-07-16"));
		assertEquals(false, jb5.friday13("2021-07-13"));
	}

	// -------------------------------------

	// Assignments #1-6

	// 1. Lambdas with PerformOperation functional interface
	@Test
	public void testPerformOp() {
		List<String> input1 = Arrays.asList("5", "1 4", "2 5", "3 898", "1 3", "2 12");
		assertEquals(Arrays.asList("EVEN", "PRIME", "PALINDROME", "ODD", "COMPOSITE"), jb5.performOps(input1));

		List<String> input2 = Arrays.asList("6", "2 0", "2 2", "2 37", "2 39", "3 3", "3 567");
		assertEquals(Arrays.asList("NOT PRIME", "PRIME", "PRIME", "COMPOSITE", "PALINDROME", "NOT PALINDROME"),
				jb5.performOps(input2));

		List<String> input3 = Arrays.asList("2", "2 f", "4 2");
		assertEquals(Arrays.asList("Input is not an integer", "Invalid test case"), jb5.performOps(input3));
	}

	// 2. Given a list of non-negative integers, return an integer list of the
	// rightmost digits
	@Test
	public void testRightDigit() {
		assertThat(Arrays.asList(1, 2, 3), is(jb5.rightDigit(Arrays.asList(1, 22, 93))));
		assertThat(Arrays.asList(6, 8, 6, 8, 1), is(jb5.rightDigit(Arrays.asList(16, 8, 886, 8, 1))));
		assertThat(Arrays.asList(0, 0), is(jb5.rightDigit(Arrays.asList(10, 0))));
	}

	// 3. Given a list of integers, return a list where each integer is multiplied
	// by 2
	@Test
	public void testDoubling() {
		assertThat(Arrays.asList(2, 4, 6), is(jb5.doubling(Arrays.asList(1, 2, 3))));
		assertThat(Arrays.asList(12, 16, 12, 16, -2), is(jb5.doubling(Arrays.asList(6, 8, 6, 8, -1))));
		assertThat(Arrays.asList(), is(jb5.doubling(Arrays.asList())));
	}

	// 4. Given a list of strings, return a list where each string has all its "x"
	// removed.
	@Test
	public void testNoX() {
		assertThat(Arrays.asList("a", "bb", "c"), is(jb5.noX(Arrays.asList("ax", "bb", "cx"))));
		assertThat(Arrays.asList("a", "bb", "c"), is(jb5.noX(Arrays.asList("xxax", "xbxbx", "xxcx"))));
		assertThat(Arrays.asList(""), is(jb5.noX(Arrays.asList("x"))));
	}

	// 5. Given an array of ints, is it possible to choose a group of some of the
	// ints, such that the group sums to the given target, with this additional
	// constraint: if there are numbers in the array that are adjacent and the
	// identical value, they must either all be chosen, or none of them chosen.
	@Test
	public void testGroupSumClump() {
		assertThat(true, is(jb5.groupSumClump(0, new int[] { 2, 4, 8 }, 10)));
		assertThat(true, is(jb5.groupSumClump(0, new int[] { 1, 2, 4, 8, 1 }, 14)));
		assertThat(false, is(jb5.groupSumClump(0, new int[] { 2, 4, 4, 8 }, 14)));
		assertThat(false, is(jb5.groupSumClump(0, new int[] {}, 99)));
	}

	// 6. Fix the below Singleton class
	@Test
	public void testSingleton() {
		assertEquals(SampleSingleton.getInstance(), SampleSingleton.getInstance());
	}

}