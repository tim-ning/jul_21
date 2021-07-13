package com.ss.jb.five;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Java Basics 5 - Methods
 * 
 * This class contains all methods for JB5 assignments:
 * 
 * Lambdas and functional interfaces and streams (1-3)
 * 
 * Date-Time API (1-7)
 * 
 * 1. Lambdas using PerformOperation
 * 
 * 2-4. Functional
 * 
 * 5. Recursion
 * 
 * The JUnit test class is located in package com.ss.jb.tests
 * 
 * @author Tim Ning
 *
 */
public class JB5 {

	// Lambdas and functional interfaces and streams

	// 1. Basic lambdas. Make an array containing a few Strings. Sort it by:

	// length (i.e., shortest to longest)
	public String[] shortestToLongest(String[] arr) {
		Arrays.sort(arr, (a, b) -> a.length() - b.length());
		return arr;
	}

	// reverse length (i.e., longest to shortest)
	public String[] longestToShortest(String[] arr) {
		Arrays.sort(arr, (a, b) -> b.length() - a.length());
		return arr;
	}

	// alphabetically by the first character only
	public String[] alphabetical(String[] arr) {
		Arrays.sort(arr, (a, b) -> a.charAt(0) - b.charAt(0));
		return arr;
	}

	// Strings that contain 'e' first, everything else second.
	public String[] eFirst1(String[] arr) {
		Arrays.sort(arr, (a, b) -> b.indexOf('e') - a.indexOf('e'));
		return arr;
	}

	// Redo the previous problem, but use a static helper method
	public String[] eFirst2(String[] arr) {
		Arrays.sort(arr, (a, b) -> JB5.eFirstHelper(a, b));
		return arr;
	}

	// helper method for 'e' first
	public static Integer eFirstHelper(String a, String b) {
		return b.indexOf('e') - a.indexOf('e');
	}

	// 2. Using Java 8 features write a method that returns a comma separated string
	// based on a given list of integers. Each element should be preceded by the
	// letter 'e' if the number is even, and preceded by the letter 'o' if the
	// number is odd.
	public String evenOdd(List<Integer> input) {
		return input.stream().map((x) -> (x % 2 == 0 ? "e" : "o") + x).collect(Collectors.joining(", "));
	}

	// 3. Given a list of Strings, write a method that returns a list of all strings
	// that start with the letter 'a' (lower case) and have exactly 3 letters.
	public List<String> a3Words(List<String> input) {
		return input.stream().filter((x) -> x.length() == 3 && x.charAt(0) == 'a').collect(Collectors.toList());
	}

	// -------------------------

	// Date-Time API questions:

	// 1. Which class would you use to store your birthday in years, months, days,
	// seconds, and nanoseconds?
	public String birthday(String date, String time, String nano) {
		String timestamp = date + "T" + time + "." + nano + "Z";
		Instant i = Instant.parse(timestamp);
		return i.toString();
	}

	// 2. Given a random date, how would you find the date of the previous Thursday?
	public String previousThursday(String date) {
		LocalDate d = LocalDate.parse(date);
		do {
			d = d.minusDays(1);
		} while (d.getDayOfWeek() != DayOfWeek.THURSDAY);
		return d.toString();
	}

	// 3. What is the difference between a ZoneId and a ZoneOffset?
	public ZoneId getZoneId() {
		// ZoneId is a string of the time zone location, for example:
		return ZoneId.of("Europe/Berlin");
	}

	public ZoneOffset getZoneOffset(LocalDateTime d) {
		// ZoneOffset is a numerical offset of the time zone from GMT/UTC, for example:
		return ZoneId.of("Europe/Berlin").getRules().getOffset(d);
	}

	// 4. How would you convert an Instant to a ZonedDateTime? How would you convert
	// a ZonedDateTime to an Instant?
	public ZonedDateTime convertToZonedDateTime(Instant i) {
		return i.atZone(ZoneId.of("Europe/Berlin"));
	}

	public Instant convertToInstant(ZonedDateTime z) {
		return z.toInstant();
	}

	// 5. Write an example that, for a given year, reports the length of each month
	// within that year.
	public List<Integer> monthLengths(int year) {
		List<Integer> list = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			YearMonth ym = YearMonth.of(year, i);
			list.add(ym.lengthOfMonth());
		}
		return list;
	}

	// 6. Write an example that, for a given month of the current year, lists all of
	// the Mondays in that month.
	public List<String> allMondays(Month month) {
		List<String> list = new ArrayList<>();
		LocalDate d = LocalDate.now().withMonth(month.getValue()).withDayOfMonth(1);

		while (d.getMonth() == month) {
			if (d.getDayOfWeek() == DayOfWeek.MONDAY)
				list.add(d.toString());
			d = d.plusDays(1);
		}
		return list;
	}

	// 7. Write an example that tests whether a given date occurs on Friday the
	// 13th.
	public boolean friday13(String date) {
		LocalDate d = LocalDate.parse(date);
		return (d.getDayOfWeek() == DayOfWeek.FRIDAY && d.getDayOfMonth() == 13);
	}

	// -------------------------------------

	// Assignments #1-5

	// 1. Write the following methods that return a lambda expression performing a
	// specified action:
	public List<String> performOps(List<String> input) {

		// The lambda expression must return if a number is odd or if it is even.
		PerformOperation isOdd = (x) -> x % 2 == 0 ? "EVEN" : "ODD";

		// The lambda expression must return if a number is prime or if it is composite.
		PerformOperation isPrime = (x) -> {
			if (x <= 1) // 0 & 1 are neither prime nor composite.
				return "NOT PRIME";
			else if (x == 2) // 2 is prime
				return "PRIME";
			else if (x % 2 == 0) // all even numbers are composite
				return "COMPOSITE";
			else {
				// check all odd divisors up to the square root
				for (int i = 3; i <= Math.sqrt(x); i += 2) {
					if (x % i == 0)
						return "COMPOSITE";
				}
			}
			return "PRIME";
		};

		// The lambda expression must return if a number is a palindrome or if it is
		// not.
		PerformOperation isPalindrome = (x) -> {
			String s = String.valueOf(x);
			int i = 0;
			int j = s.length() - 1;

			// start from both ends & check every char towards the center
			while (i < j) {
				if (s.charAt(i) != s.charAt(j))
					return "NOT PALINDROME";
				i++;
				j--;
			}
			return "PALINDROME";
		};

		// parse the integer pairs & return results as a list
		List<String> output = input.stream().filter(i -> i.contains(" ")).map(s -> {
			Integer x = null;
			try {
				x = Integer.parseInt(s.substring(2));
			} catch (Exception e) {
				return "Input is not an integer";
			}
			switch (s.charAt(0)) {
			case '1':
				return isOdd.doMath(x);
			case '2':
				return isPrime.doMath(x);
			case '3':
				return isPalindrome.doMath(x);
			default:
				return "Invalid test case";
			}
		}).collect(Collectors.toList());

		return output;
	}

	// 2. Given a list of non-negative integers, return an integer list of the
	// rightmost digits
	public List<Integer> rightDigit(List<Integer> input) {
		return input.stream().map((x) -> x % 10).collect(Collectors.toList());
	}

	// 3. Given a list of integers, return a list where each integer is multiplied
	// by 2
	public List<Integer> doubling(List<Integer> input) {
		return input.stream().map((x) -> x * 2).collect(Collectors.toList());
	}

	// 4. Given a list of strings, return a list where each string has all its "x"
	// removed.
	public List<String> noX(List<String> input) {
		return input.stream().map((x) -> x.replace("x", "")).collect(Collectors.toList());
	}

	// 5. Given an array of ints, is it possible to choose a group of some of the
	// ints, such that the group sums to the given target, with this additional
	// constraint: if there are numbers in the array that are adjacent and the
	// identical value, they must either all be chosen, or none of them chosen.
	public boolean groupSumClump(int n, int[] arr, int target) {

		// trivial case
		if (arr.length == 0)
			return false;

		// first, combine the adjacent identical numbers into a new array
		List<Integer> list = new ArrayList<>();
		int clump = arr[0]; // sum of adjacent identical numbers

		for (int i = 1; i < arr.length; i++) {
			if (arr[i] != arr[i - 1]) {
				list.add(clump);
				clump = 0;
			}
			clump += arr[i];
		}
		list.add(clump);

		// convert list to array
		int[] set = list.stream().mapToInt(i -> i).toArray();

		// now this can be solved the same way as the subset sum problem
		return subsetSum(set.length, set, target);
	}

	// decide whether any subset of the integers sums to the target
	public boolean subsetSum(int n, int[] arr, int target) {

		if (target == 0) // found a solution
			return true;

		if (n == 0) // reached a dead end
			return false;

		// branch 1 - discarding the last number
		boolean b1 = subsetSum(n - 1, arr, target);

		// branch 2 - subtracting the last number from the target
		boolean b2 = (arr[n - 1] <= target) ? subsetSum(n - 1, arr, target - arr[n - 1]) : false;

		return b1 || b2;
	}

}
