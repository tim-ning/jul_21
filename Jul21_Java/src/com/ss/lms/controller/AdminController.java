package com.ss.lms.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.ss.lms.domain.Author;
import com.ss.lms.domain.Book;
import com.ss.lms.domain.BookLoan;
import com.ss.lms.domain.Borrower;
import com.ss.lms.domain.Branch;
import com.ss.lms.domain.Entity;
import com.ss.lms.domain.Genre;
import com.ss.lms.domain.Publisher;
import com.ss.lms.presentation.MenuManager;
import com.ss.lms.service.AdminService;

public class AdminController extends BaseController {

	private AdminService adminServ;

	// temporary vars for creating/updating a book
	private int nBookId;
	private String nTitle;
	private List<Author> nAuthors;
	private List<Genre> nGenres;
	private Publisher nPublisher;
	private Branch nBranch;
	private int nCopies;

	public AdminController(MenuManager mm) throws SQLException {
		super(mm);
		adminServ = new AdminService();
		resetBook();
	}

	// administrator operation choices, received from menu
	@Override
	public void performAction(String actionStr, int id) throws SQLException {

		switch (actionStr) {

		// Genre operations
		case "AddGenre":
			addGenre();
			break;
		case "UpdateGenre":
			updateGenre();
			break;
		case "DeleteGenre":
			deleteGenre();
			break;
		case "ReadGenre":
			readGenre();
			break;

		// Publisher operations
		case "AddPublisher":
			addPublisher();
			break;
		case "UpdatePublisher":
			updatePublisher();
			break;
		case "DeletePublisher":
			deletePublisher();
			break;
		case "ReadPublisher":
			readPublisher();
			break;

		// Borrower operations
		case "AddBorrower":
			addBorrower();
			break;
		case "UpdateBorrower":
			updateBorrower();
			break;
		case "DeleteBorrower":
			deleteBorrower();
			break;
		case "ReadBorrower":
			readBorrower();
			break;

		// Branch operations
		case "AddBranch":
			addBranch();
			break;
		case "UpdateBranch":
			updateBranchAdmin();
			break;
		case "DeleteBranch":
			deleteBranch();
			break;
		case "ReadBranch":
			readBranchesAdmin();
			break;

		// Author operations
		case "AddAuthor":
			addAuthor();
			break;
		case "UpdateAuthor":
			updateAuthor();
			break;
		case "DeleteAuthor":
			deleteAuthor();
			break;
		case "ReadAuthor":
			readAuthor();
			break;

		// Book operations
		case "AddBook":
			addBook();
			break;
		case "SelectUpdateBook":
			selectUpdateBook();
			break;
		case "UpdateBook":
			updateBook();
			break;
		case "DeleteBook":
			deleteBook();
			break;
		case "ReadBook":
			readBooksAdmin();
			break;

		// creating/updating a book
		case "SetBookId":
			setBookId();
			break;
		case "SetTitle":
			setTitle();
			break;
		case "SetAuthors":
			setAuthors();
			break;
		case "SetGenres":
			setGenres();
			break;
		case "SetPublisher":
			setPublisher();
			break;
		case "SetBranch":
			setBranch();
			break;
		case "SetCopies":
			setCopies();
			break;
		case "ResetBook":
			resetBook();
			break;

		// other
		case "OverrideDate":
			overrideDueDate();
			break;
		case "RandGenres":
			addRandomGenres();
			break;

		default:
			System.out.println("[!] Unhandled action: " + actionStr + endl);
		}
	}

	// ---- creating or updating a book ----

	// show the fields for the partially created/updated book above the menu choices
	public void showPartialBook(boolean createMode) {
		StringBuilder sb = new StringBuilder();
		sb.append("--------------------- " + (createMode ? "Create a new Book" : "Update a Book")
				+ " -----------------------" + endl + endl + "Input fields:");
		sb.append(endl + "\t" + "Book ID: " + (nBookId != -1 ? nBookId : ""));
		sb.append(endl + "\t" + "Title: " + (nTitle != null ? nTitle : ""));
		sb.append(endl + "\t" + "Authors: ");
		sb.append(nAuthors.stream().map(x -> x.getName()).collect(Collectors.joining(", ")));
		sb.append(endl + "\t" + "Genres: ");
		sb.append(nGenres.stream().map(x -> x.getName()).collect(Collectors.joining(", ")));
		sb.append(endl + "\t" + "Publisher: ");
		if (nPublisher != null)
			sb.append(nPublisher.getName());
		if (createMode) {
			sb.append(endl + "\t" + "Library Branch: ");
			if (nBranch != null)
				sb.append(nBranch.getName());
			sb.append(endl + "\t" + "Number of Copies: " + (nCopies != -1 ? nCopies : ""));
		}
		System.out.println(sb.toString() + endl);
	}

	private void setBookId() throws SQLException {
		int id = readInt("Please enter a new Book ID:");
		if (id == -1)
			return;
		if (adminServ.readBookById(id) != null) {
			printIdExists();
			return;
		}
		nBookId = id;
	}

	private void setTitle() throws SQLException {
		nTitle = readString("title");
	}

	private void setAuthors() throws SQLException {
		readAuthor();
		int id = readIntWithClear("Author");
		if (id == -2) {
			nAuthors = new ArrayList<>();
			return;
		}
		Author a = adminServ.readAuthorById(id);
		if (a == null) {
			printInvalidId();
			return;
		}
		if (nAuthors.contains(a)) {
			System.out.println("Author is already in the list." + endl);
			return;
		}
		nAuthors.add(a);
	}

	private void setGenres() throws SQLException {
		readGenre();
		int id = readIntWithClear("Genre");
		if (id == -2) {
			nGenres = new ArrayList<>();
			return;
		}
		Genre g = adminServ.readGenreById(id);
		if (g == null) {
			printInvalidId();
			return;
		}
		if (nGenres.contains(g)) {
			System.out.println("Genre is already in the list." + endl);
			return;
		}
		nGenres.add(g);
	}

	private void setPublisher() throws SQLException {
		readPublisher();
		int id = readInt("Please enter Publisher ID:");
		Publisher p = adminServ.readPublisherById(id);
		if (p == null) {
			printInvalidId();
			return;
		}
		nPublisher = p;
	}

	private void setBranch() throws SQLException {
		readBranchesAdmin();
		int id = readInt("Please enter Library Branch ID:");
		Branch b = adminServ.readBranchById(id);
		if (b == null) {
			printInvalidId();
			return;
		}
		nBranch = b;
	}

	private void setCopies() throws SQLException {
		nCopies = readInt("Please enter number of copies:");
	}

	// clear temporary input fields
	private void resetBook() {
		nBookId = -1;
		nTitle = null;
		nAuthors = new ArrayList<>();
		nGenres = new ArrayList<>();
		nPublisher = null;
		nBranch = null;
		nCopies = -1;
	}

	// ---- Book operations ----

	// submit the input fields
	private void addBook() throws SQLException {
		// validate all input fields
		if (nBookId == -1) {
			printBookFailed("Book ID");
			return;
		}
		if (nTitle == null) {
			printBookFailed("Title");
			return;
		}
		if (nAuthors.size() == 0) {
			printBookFailed("Author");
			return;
		}
		if (nPublisher == null) {
			printBookFailed("Publisher");
			return;
		}
		if (nBranch == null) {
			printBookFailed("Library Branch");
			return;
		}
		if (nCopies == -1) {
			printBookFailed("Number of Copies");
			return;
		}

		boolean hasCopies = (adminServ.readCopiesByIds(nBookId, nBranch.getId()) != null);
		adminServ.addBook(nBookId, nTitle, nAuthors, nGenres, nPublisher.getId(), nBranch.getId(), nCopies, hasCopies);
		resetBook();
		menuManager.setMenu("@admin2");
	}

	// pick book ID & initialize the temporary input fields
	private void selectUpdateBook() throws SQLException {
		int id = readInt("Enter Book ID to update:");

		Book b = adminServ.readBookById(id);
		if (b != null) {
			// set all temporary vars
			nAuthors.add(b.getAuthor());
			List<Author> coauthors = adminServ.readAuthorsByBookId(b.getId());
			for (Author a : coauthors) {
				if (a.getId() != b.getAuthId())
					nAuthors.add(a);
			}

			nGenres = adminServ.readGenresByBookId(b.getId());
			nPublisher = adminServ.readPublisherById(b.getPubId());
			nTitle = b.getTitle();
			nBookId = b.getId();

			menuManager.setMenu("@adminB2");
		} else
			printInvalidId();
	}

	// submit the input fields
	private void updateBook() throws SQLException {
		// validate all input fields
		if (nAuthors.size() == 0) {
			printBookFailed("Author");
			return;
		}

		adminServ.updateBook(nBookId, nTitle, nAuthors, nGenres, nPublisher.getId());
		resetBook();
		menuManager.setMenu("@admin2");
	}

	// input fields are missing (for add/update book)
	private void printBookFailed(String field) {
		System.out.println("Book is incomplete: " + field + " must have a value." + endl);
	}

	private void deleteBook() throws SQLException {
		int id = readInt("Enter Book ID to delete:");
		if (adminServ.readBookById(id) != null)
			adminServ.deleteBook(id);
		else
			printInvalidId();
	}

	// print all books by ID.
	// show the id, title, authors, genres, & publisher.
	private void readBooksAdmin() throws SQLException {
		System.out.println("Read - Books by ID:");
		List<Book> books = adminServ.readAllBooks();

		for (int i = 0; i < books.size(); i++) {
			StringBuilder sb = new StringBuilder();

			Book b = books.get(i);
			sb.append("\t" + b.getId() + ") " + b.getTitle() + endl);
			sb.append("\t" + "Authors: " + b.getAuthName());

			List<Author> coauthors = adminServ.readAuthorsByBookId(b.getId());
			for (Author a : coauthors) {
				if (a.getId() != b.getAuthId())
					sb.append(", " + a.getName());
			}

			List<Genre> g = adminServ.readGenresByBookId(b.getId());
			sb.append(endl + "\t" + "Genres: ");
			sb.append(g.stream().map(x -> x.getName()).collect(Collectors.joining(", ")));

			Publisher p = adminServ.readPublisherById(b.getPubId());
			sb.append(endl + "\t" + "Publisher: " + p.getName() + endl);

			System.out.println(sb.toString());
		}
	}

	// ---- Author operations ----

	private void addAuthor() throws SQLException {
		int id = readInt("Enter Author ID to add:");
		if (id == -1)
			return;
		if (adminServ.readAuthorById(id) != null) {
			printIdExists();
			return;
		}
		String input = readString("author name");
		if (input == null)
			return;
		adminServ.addAuthor(id, input);
	}

	private void updateAuthor() throws SQLException {
		int id = readInt("Enter Author ID to update:");
		Author a = adminServ.readAuthorById(id);
		if (a != null) {
			a.printFields();
			String input = readString("author name");
			if (input == null)
				return;
			adminServ.updateAuthor(id, input);
		} else
			printInvalidId();
	}

	private void deleteAuthor() throws SQLException {
		int id = readInt("Enter Author ID to delete:");
		if (adminServ.readAuthorById(id) != null)
			adminServ.deleteAuthor(id);
		else
			printInvalidId();
	}

	private void readAuthor() throws SQLException {
		printListById("Authors by ID", adminServ.readAllAuthors());
	}

	// ---- Genre operations ----

	// initially populate the book_genres table.
	// adding duplicate (genreId, bookId) combos is allowed,
	// but adding non-existent bookIds is not allowed.
	private void addRandomGenres() throws SQLException {
		Random rand = new Random();
		for (int bookId = 1; bookId <= 1000; bookId++) {
			int genreId = rand.nextInt(12) + 16;
			adminServ.addGenreByBookId(genreId, bookId);
		}
	}

	private void addGenre() throws SQLException {
		int id = readInt("Enter Genre ID to add:");
		if (id == -1)
			return;
		if (adminServ.readGenreById(id) != null) {
			printIdExists();
			return;
		}
		String input = readString("genre name");
		if (input == null)
			return;
		adminServ.addGenre(id, input);
	}

	private void updateGenre() throws SQLException {
		int id = readInt("Enter Genre ID to update:");
		Genre g = adminServ.readGenreById(id);
		if (g != null) {
			g.printFields();
			String input = readString("genre name");
			if (input == null)
				return;
			adminServ.updateGenre(id, input);
		} else
			printInvalidId();
	}

	private void deleteGenre() throws SQLException {
		int id = readInt("Enter Genre ID to delete:");
		if (adminServ.readGenreById(id) != null)
			adminServ.deleteGenre(id);
		else
			printInvalidId();
	}

	private void readGenre() throws SQLException {
		printListById("Genres by ID", adminServ.readAllGenres());
	}

	// ---- Publisher operations ----

	private void addPublisher() throws SQLException {
		int id = readInt("Enter Publisher ID to add:");
		if (id == -1)
			return;
		if (adminServ.readPublisherById(id) != null) {
			printIdExists();
			return;
		}
		String[] input = readStrings(new String[] { "publisher name", "publisher address", "publisher phone" }, false);
		if (input == null)
			return;
		adminServ.addPublisher(id, input[0], input[1], input[2]);
	}

	private void updatePublisher() throws SQLException {
		int id = readInt("Enter Publisher ID to update:");
		Publisher p = adminServ.readPublisherById(id);
		if (p != null) {
			p.printFields();
			String[] input = readStrings(new String[] { "publisher name", "publisher address", "publisher phone" },
					true);
			if (input == null)
				return;
			adminServ.updatePublisher(id, input[0], input[1], input[2]);
		} else
			printInvalidId();
	}

	private void deletePublisher() throws SQLException {
		int id = readInt("Enter Publisher ID to delete:");
		if (adminServ.readPublisherById(id) != null)
			adminServ.deletePublisher(id);
		else
			printInvalidId();
	}

	private void readPublisher() throws SQLException {
		printListById("Publishers by ID", adminServ.readAllPublishers());
	}

	// ---- Borrower operations ----

	private void addBorrower() throws SQLException {
		int id = readInt("Enter Borrower card number to add:");
		if (id == -1)
			return;
		if (adminServ.readBorrowerByCardNo(id) != null) {
			printIdExists();
			return;
		}
		String[] input = readStrings(new String[] { "borrower name", "borrower address", "borrower phone" }, false);
		if (input == null)
			return;
		adminServ.addBorrower(id, input[0], input[1], input[2]);
	}

	private void updateBorrower() throws SQLException {
		int id = readInt("Enter Borrower card number to update:");
		Borrower b = adminServ.readBorrowerByCardNo(id);
		if (b != null) {
			b.printFields();
			String[] input = readStrings(new String[] { "borrower name", "borrower address", "borrower phone" }, true);
			if (input == null)
				return;
			adminServ.updateBorrower(id, input[0], input[1], input[2]);
		} else
			printInvalidId();
	}

	private void deleteBorrower() throws SQLException {
		int id = readInt("Enter Borrower card number to delete:");
		if (adminServ.readBorrowerByCardNo(id) != null)
			adminServ.deleteBorrower(id);
		else
			printInvalidId();
	}

	private void readBorrower() throws SQLException {
		printListById("Borrowers by Card Number", adminServ.readAllBorrowers());
	}

	// ---- Branch operations ----

	private void addBranch() throws SQLException {
		int id = readInt("Enter Branch ID to add:");
		if (id == -1)
			return;
		if (adminServ.readBranchById(id) != null) {
			printIdExists();
			return;
		}
		String[] input = readStrings(new String[] { "branch name", "branch address" }, false);
		if (input == null)
			return;
		adminServ.addBranch(id, input[0], input[1]);
	}

	private void deleteBranch() throws SQLException {
		int id = readInt("Enter Branch ID to delete:");
		if (adminServ.readBranchById(id) != null)
			adminServ.deleteBranch(id);
		else
			printInvalidId();
	}

	private void updateBranchAdmin() throws SQLException {
		int id = readInt("Enter Branch ID to update:");
		Branch b = adminServ.readBranchById(id);
		if (b != null) {
			b.printFields();
			String[] input = readStrings(new String[] { "branch name", "branch address" }, true);
			if (input == null)
				return;
			adminServ.updateBranch(id, input[0], input[1]);
		} else
			printInvalidId();
	}

	private void readBranchesAdmin() throws SQLException {
		printListById("Branches by ID", adminServ.readAllBranches());
	}

	// ---- book loan operations ----

	// multiple inputs. failing any step reverts to admin menu.
	private void overrideDueDate() throws SQLException {

		// enter & validate borrower cardNo
		int cardNo = readInt("Enter borrower card number:");
		Borrower b = adminServ.readBorrowerByCardNo(cardNo);
		if (b == null) {
			System.out.println("Invalid card number." + endl);
			return;
		}

		// show a list of existing loans
		List<String> list = getLoansByCardNo(adminServ, cardNo, true);
		if (list.size() == 0) {
			System.out
					.println("Borrower " + b.getName() + " does not have any books checked out at any branch." + endl);
			return;
		}
		System.out.println("Existing loans for borrower: " + b.getName());
		for (String s : list)
			System.out.println(s);

		// pick a loan & validate input range
		int choice = readInt("Select a book loan to update:");
		if (choice <= 0 || choice > bookLoans.size()) {
			System.out.println("Invalid option." + endl);
			return;
		}

		// input # of days
		String s = readString("number of days to extend this book loan");
		if (s == null)
			return;
		int days;
		try {
			days = Integer.parseInt(s);
		} catch (Exception ex) {
			System.out.println("Input must be an integer." + endl);
			return;
		}

		// update the entry
		BookLoan loan = bookLoans.get(choice - 1);
		LocalDateTime d = loan.getDueDate().plusDays(days);
		adminServ.updateDueDate(loan.getBookId(), loan.getBranchId(), loan.getCardNo(), d.format(formatter));
	}

	// ------------ helper methods ------------

	// print a list of entity objects by ID, as admin menu choices
	private <T> void printListById(String title, List<T> list) {
		System.out.println("Read - " + title + ":");
		for (T item : list) {
			Entity e = (Entity) item;
			System.out.println("\t" + e.getId() + ") " + e.getName());
		}
		System.out.println();
	}

	// user tried to update/delete/read an ID that doesn't exist in the table
	private void printInvalidId() {
		System.out.println("Invalid ID." + endl);
	}

	// user tried to add a unique ID that already exists in the table
	private void printIdExists() {
		System.out.println("That ID already exists." + endl);
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
	 * 
	 * 
	 * 
	 */
}
