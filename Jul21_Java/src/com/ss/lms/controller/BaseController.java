package com.ss.lms.controller;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ss.lms.domain.Book;
import com.ss.lms.domain.BookLoan;
import com.ss.lms.domain.Branch;
import com.ss.lms.presentation.MenuManager;
import com.ss.lms.service.BaseService;

/*
 * Contains methods used by more than 1 controller type
 */
public abstract class BaseController {

	// temporary cache of menu choices the user can select from
	protected List<Branch> branches;
	protected List<Book> books;
	protected List<BookLoan> bookLoans;

	protected MenuManager menuManager;
	protected Branch selectedBranch;
	protected Book selectedBook;
	protected String dtPattern;
	protected DateTimeFormatter formatter;
	protected final String endl = System.lineSeparator();

	public BaseController(MenuManager menuManager) {
		dtPattern = BookLoan.getDateTimePattern();
		formatter = DateTimeFormatter.ofPattern(dtPattern);
		this.menuManager = menuManager;
	}

	public abstract void performAction(String actionStr, int id) throws SQLException;

	// retrieve from cache
	protected Branch getBranchById(int id) {
		for (Branch b : branches) {
			if (b.getId() == id)
				return b;
		}
		return null;
	}

	protected Book getBookById(int id) {
		for (Book b : books) {
			if (b.getId() == id)
				return b;
		}
		return null;
	}

	// menu selection
	public void selectBranchById(int id) {
		selectedBranch = getBranchById(id);
	}

	public void selectBookById(int id) {
		selectedBook = getBookById(id);
	}

	public List<Branch> readAllBranches(BaseService serv) throws SQLException {
		branches = serv.readAllBranches();
		return branches;
	}

	public List<Book> readAllBooks(BaseService serv) throws SQLException {
		books = serv.readAllBooks();
		return books;
	}

	// get a list of this borrower's current loans (for borrower & admin)
	// show: books, branches, & due dates
	protected List<String> getLoansByCardNo(BaseService serv, int cardNo, boolean showIndices) throws SQLException {
		List<String> output = new ArrayList<>();
		bookLoans = serv.readLoansByCardNo(cardNo, false);

		for (int i = 0; i < bookLoans.size(); i++) {
			BookLoan loan = bookLoans.get(i);
			StringBuilder sb = new StringBuilder();
			sb.append(showIndices ? "\t" + (i + 1) + ") " : "\t");

			Branch br = serv.readBranchById(loan.getBranchId());
			sb.append("Branch: " + br.getName() + endl);

			Book ba = serv.readBookById(loan.getBookId());
			sb.append("\t" + "Book: " + ba.getName() + endl);

			sb.append("\t" + "Due date: " + loan.getDueDate().format(formatter) + endl);
			output.add(sb.toString());
		}
		return output;
	}

	// ------- input readers -------

	// read multiple input fields (columns) into a string array.
	// used for add & update (update allows skipping fields).
	protected String[] readStrings(String[] columns, boolean updateMode) {
		System.out.println("Enter 'quit' at any prompt to cancel operation." + endl);

		String[] inputs = new String[columns.length];
		Scanner scan = new Scanner(System.in);

		for (int i = 0; i < columns.length; i++) {
			System.out.println("Please enter new " + columns[i] + (updateMode ? " or enter N/A for no change:" : ":"));

			try {
				inputs[i] = scan.nextLine();
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}

			if (inputs[i].equalsIgnoreCase("quit")) {
				System.out.println("Operation cancelled." + endl);
				return null;
			}

			// can skip an input field by either typing "n/a", or just pressing enter
			if (updateMode && inputs[i].equalsIgnoreCase("n/a"))
				inputs[i] = "";
		}
		return inputs;
	}

	// read 1 string input field
	protected String readString(String column) {
		System.out.println("Enter 'quit' to cancel operation." + endl);
		System.out.println("Please enter new " + column + ":");
		String input = "";

		Scanner scan = new Scanner(System.in);
		try {
			input = scan.nextLine();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		if (input.equalsIgnoreCase("quit")) {
			System.out.println("Operation cancelled." + endl);
			return null;
		}
		return input;
	}

	// for admin adding authors & genres
	protected int readIntWithClear(String column) {
		System.out.println("Please enter " + column + " ID to add, or type 'clear' to clear " + column + "s:");
		int choice = -1;

		Scanner scan = new Scanner(System.in);
		try {
			String s = scan.nextLine();
			if (s.equalsIgnoreCase("clear"))
				return -2;
			choice = Integer.parseInt(s);
		} catch (Exception ex) {
			System.out.println("Input must be an integer." + endl);
			return -1;
		}
		if (choice < 0) {
			System.out.println("Input must be a positive integer." + endl);
			return -1;
		}
		return choice;
	}

	// read a positive integer
	// returns -1 on unparseable string or negative number
	protected int readInt(String prompt) {
		System.out.println(prompt);
		int choice = -1;

		Scanner scan = new Scanner(System.in);
		try {
			choice = Integer.parseInt(scan.nextLine());
		} catch (Exception ex) {
			System.out.println("Input must be an integer." + endl);
			return -1;
		}
		if (choice < 0) {
			System.out.println("Input must be a positive integer." + endl);
			return -1;
		}
		return choice;
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
}
