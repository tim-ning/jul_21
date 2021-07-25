package com.ss.lms.controller;

import java.sql.SQLException;
import java.util.List;

import com.ss.lms.domain.Book;
import com.ss.lms.domain.BookCopies;
import com.ss.lms.domain.Branch;
import com.ss.lms.presentation.MenuManager;
import com.ss.lms.service.LibrarianService;

public class LibrarianController extends BaseController {

	private LibrarianService libServ;

	public LibrarianController(MenuManager mm) throws SQLException {
		super(mm);
		libServ = new LibrarianService();
	}

	// librarian actions, received from menu
	@Override
	public void performAction(String actionStr, int id) throws SQLException {
		switch (actionStr) {

		case "SelBranch": // librarian selects from all branches
			selectBranchById(id);
			break;

		case "UpdateBranch": // librarian updates branch info
			updateBranchLibrarian();
			break;

		case "UpdateCopies": // librarian adds copies of a book
			selectBookById(id);
			updateCopies();
			break;

		default:
			System.out.println("[!] Unhandled action: " + actionStr + endl);
		}
	}

	public List<Branch> getAllBranches() throws SQLException {
		branches = libServ.readAllBranches();
		return branches;
	}

	public List<Book> getAllBooks() throws SQLException {
		books = libServ.readAllBooks();
		return books;
	}

	public void updateBranchLibrarian() throws SQLException {
		selectedBranch.printFields();

		String[] inputs = readStrings(new String[] { "branch name", "branch address" }, true);
		if (inputs == null) // operation cancelled, or invalid input field
			return;

		// update the db entry
		libServ.updateBranch(selectedBranch.getId(), inputs[0], inputs[1]);

		// update the cached selected branch
		selectedBranch = libServ.readBranchById(selectedBranch.getId());
	}

	// update # of book copies
	public void updateCopies() throws SQLException {
		System.out.println("Branch: " + selectedBranch.getName());
		System.out.println("Book: " + selectedBook.getName());

		// distinguish between: the entry exists & is zero, or the entry doesn't exist
		BookCopies bc = libServ.readCopiesByIds(selectedBook.getId(), selectedBranch.getId());
		String numCopiesStr = (bc != null) ? String.valueOf(bc.getNoOfCopies()) : "None";
		System.out.println("Existing number of copies: " + numCopiesStr + endl);

		int copies = readInt("Enter new number of copies:");
		if (copies == -1)
			return;

		// update the db entry
		if (bc != null)
			libServ.updateCopies(selectedBook.getId(), selectedBranch.getId(), copies);
		else
			libServ.createCopies(selectedBook.getId(), selectedBranch.getId(), copies);
	}

}
