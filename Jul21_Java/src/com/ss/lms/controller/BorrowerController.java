package com.ss.lms.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.ss.lms.domain.Book;
import com.ss.lms.domain.BookLoan;
import com.ss.lms.domain.Borrower;
import com.ss.lms.domain.Branch;
import com.ss.lms.presentation.MenuManager;
import com.ss.lms.service.BorrowerService;

public class BorrowerController extends BaseController {

	private BorrowerService borrServ;
	private int currentCardNo;

	public BorrowerController(MenuManager mm) throws SQLException {
		super(mm);
		borrServ = new BorrowerService();
	}

	// borrower actions, received from menu
	@Override
	public void performAction(String actionStr, int id) throws SQLException {
		switch (actionStr) {

		case "Login": // validate borrower card number
			if (validateCard())
				menuManager.setMenu("@borr1");
			break;

		case "SelBranch1": // borrower selects from all branches for checkout
			selectBranchById(id);
			break;

		case "SelBranch2": // borrower selects from branches where loans exist, for returns
			selectBranchById(id);
			break;

		case "Checkout": // borrower checks out a book
			checkoutBookById(id);
			break;

		case "Return": // borrower returns a book
			returnBookById(id);
			break;

		case "ViewLoans": // show all loans the borrower currently has
			viewLoans();
			break;

		default:
			System.out.println("[!] Unhandled action: " + actionStr + endl);
		}
	}

	public List<Branch> getAllBranches() throws SQLException {
		branches = borrServ.readAllBranches();
		return branches;
	}

	public List<Book> getAllBooks() throws SQLException {
		books = borrServ.readAllBooks();
		return books;
	}

	// get a list of branches where this borrower has books checked out
	public List<Branch> getBranchesByCardNo() throws SQLException {
		branches = borrServ.readBranchesByCardNo(currentCardNo);
		return branches;
	}

	// get list of books available to checkout for a branch
	public List<Book> getBorrowerCheckoutList() throws SQLException {
		books = borrServ.readBooksByBranch(selectedBranch.getId());
		return books;
	}

	// get list of books the borrower currently has checked out at this branch
	public List<Book> getBorrowerReturnList() throws SQLException {
		books = borrServ.readBooksByLoan(selectedBranch.getId(), currentCardNo);
		return books;
	}

	// borrower login
	public boolean validateCard() throws SQLException {
		int cardNo = readInt("Enter your card number:");
		if (cardNo == -1)
			return false;

		Borrower b = borrServ.readBorrowerByCardNo(cardNo);
		if (b != null) {
			currentCardNo = cardNo;
			System.out.println("Welcome: " + b.getName() + endl);
			return true;
		} else {
			System.out.println("Invalid card number." + endl);
			return false;
		}
	}

	// --------- loan methods ------------

	// show all of the borrower's current loans (to facilitate returning a book)
	public void viewLoans() throws SQLException {
		List<String> list = getLoansByCardNo(borrServ, currentCardNo, false);
		if (list.size() == 0) {
			System.out.println("You do not have any books checked out at any branches." + endl);
			return;
		}

		System.out.println("You have the following books checked out:");
		for (String s : list)
			System.out.println(s);
	}

	// if borrower doesn't already have the book, then add a loan & subtract a copy.
	public void checkoutBookById(int bookId) throws SQLException {
		selectedBook = getBookById(bookId);
		String dateOut = LocalDateTime.now().format(formatter);
		String dueDate = LocalDateTime.now().plusDays(7).format(formatter); // 1 week from today

		// determine whether the loan row exists, & whether the book is checked out
		int loanState = getLoanState(selectedBook.getId(), selectedBranch.getId());
		if (loanState != 1)
			borrServ.createLoan(selectedBook.getId(), selectedBranch.getId(), currentCardNo, dateOut, dueDate,
					loanState);
		else
			System.out.println("You already have this book checked out." + endl);
	}

	// return the loan & add a copy.
	// we've already verified that the borrower has this book checked out, via
	// filtering the available menu choices.
	// if the (book id, branch id) combination doesn't exist in table book_copies,
	// then create it.
	public void returnBookById(int bookId) throws SQLException {
		selectedBook = getBookById(bookId);
		String dateIn = LocalDateTime.now().format(formatter);
		boolean hasCopies = (borrServ.readCopiesByIds(selectedBook.getId(), selectedBranch.getId()) != null);
		borrServ.returnLoan(selectedBook.getId(), selectedBranch.getId(), currentCardNo, dateIn, hasCopies);
	}

	// trinary state flag:
	// loan is allowed if: (2) the row exists & dateIn is filled,
	// or (0) the row doesn't exist.
	// load is not allowed if: (1) the row exists & dateIn is null.
	private int getLoanState(int bookId, int branchId) throws SQLException {
		List<BookLoan> list = borrServ.readLoansByCardNo(currentCardNo, true);
		for (BookLoan loan : list) {
			if (loan.getBookId() == bookId && loan.getBranchId() == branchId) {
				if (loan.getDateIn() == null)
					return 1; // the row exists, & dateIn is null
				else
					return 2; // the row exists, & dateIn is filled
			}
		}
		return 0; // the row does not exist
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
