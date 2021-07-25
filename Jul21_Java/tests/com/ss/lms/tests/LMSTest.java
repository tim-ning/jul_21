package com.ss.lms.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ss.lms.presentation.*;
import com.ss.lms.controller.*;
import com.ss.lms.service.*;
import com.ss.lms.dao.*;
import com.ss.lms.domain.*;

/**
 * JUnit tests for the LMS project (located in package src/com/ss/lms)
 * 
 * @author Tim Ning
 *
 */
public class LMSTest {

	// borrower checks out a book and then returns it
	@Test
	public void testBorrowerLoan() throws SQLException {
		BorrowerService borrServ = new BorrowerService();
		BookLoan b = new BookLoan(2, 1, 485, "2021-07-30 23:23:23", "2021-08-10 23:23:23", null);
		DateTimeFormatter f = DateTimeFormatter.ofPattern(BookLoan.getDateTimePattern());
		borrServ.createLoan(b.getBookId(), b.getBranchId(), b.getCardNo(), b.getDateOut().format(f),
				b.getDueDate().format(f), 2);
		List<BookLoan> list = borrServ.readLoansByCardNo(485, false);
		assertEquals(true, list.contains(b));
		assertEquals(1, borrServ.readBooksByLoan(1, 485).size());
		assertEquals(0, borrServ.readCopiesByIds(2, 1).getNoOfCopies());
		borrServ.returnLoan(2, 1, 485, "2021-07-30 23:23:23", true);
		list = borrServ.readLoansByCardNo(485, false);
		assertEquals(false, list.contains(b));
		assertEquals(0, borrServ.readBooksByLoan(1, 485).size());
		assertEquals(1, borrServ.readCopiesByIds(2, 1).getNoOfCopies());
	}

	// librarian updates branch details
	@Test
	public void testLibrarianBranchUpdate() throws SQLException {
		LibrarianService libServ = new LibrarianService();
		libServ.updateBranch(9, "FooBar", "123 Main St.");
		Branch b0 = libServ.readBranchById(9);
		assertEquals("FooBar", b0.getName());
		assertEquals("123 Main St.", b0.getAddress());
		libServ.updateBranch(9, "Branch 9", "999 Address Rd.");
		Branch b1 = libServ.readBranchById(9);
		assertEquals("Branch 9", b1.getName());
		assertEquals("999 Address Rd.", b1.getAddress());
	}

	// admin add/update/delete genre
	@Test
	public void testGenre() throws SQLException {
		AdminService adminServ = new AdminService();
		Genre g = new Genre(30, "Unknown");
		adminServ.addGenre(g.getId(), g.getName());
		assertEquals(g, adminServ.readGenreById(30));
		adminServ.addGenreByBookId(30, 2);
		assertEquals(true, adminServ.readGenresByBookId(2).contains(g));
		Genre g1 = new Genre(30, "Genre 30");
		adminServ.updateGenre(g1.getId(), g1.getName());
		assertEquals(g1, adminServ.readGenreById(30));
		adminServ.deleteGenre(30);
		assertNull(adminServ.readGenreById(30));
		assertEquals(false, adminServ.readGenresByBookId(2).contains(g));
		assertEquals(false, adminServ.readGenresByBookId(2).contains(g1));
	}

	// admin add/update/delete author
	@Test
	public void testAuthor() throws SQLException {
		AdminService adminServ = new AdminService();
		adminServ.addAuthor(505, "Hello World");
		assertEquals("Hello World", adminServ.readAuthorById(505).getName());
		adminServ.updateAuthor(505, "Hello");
		assertEquals("Hello", adminServ.readAuthorById(505).getName());
		adminServ.deleteAuthor(505);
		assertNull(adminServ.readAuthorById(505));
	}

	// admin add/update/delete publisher
	@Test
	public void testPublisher() throws SQLException {
		AdminService adminServ = new AdminService();
		Publisher p = new Publisher(102, "Bob", "123 Main St.", "777-777-7777");
		adminServ.addPublisher(p.getId(), p.getName(), p.getPublisherAddress(), p.getPublisherPhone());
		assertEquals(p, adminServ.readPublisherById(102));
		adminServ.updatePublisher(102, "Bob2", "", "");
		assertEquals("Bob2", adminServ.readPublisherById(102).getName());
		adminServ.deletePublisher(102);
		assertNull(adminServ.readPublisherById(102));
	}

	// admin add/update/delete borrower
	@Test
	public void testBorrower() throws SQLException {
		AdminService adminServ = new AdminService();
		adminServ.addBorrower(505, "Pojo", "Array borrower[505]", "No phone");
		Borrower b0 = adminServ.readBorrowerByCardNo(505);
		assertEquals("Pojo", b0.getName());
		assertEquals("Array borrower[505]", b0.getAddress());
		assertEquals("No phone", b0.getPhone());
		adminServ.updateBorrower(505, "", "New address", "New phone");
		Borrower b1 = adminServ.readBorrowerByCardNo(505);
		assertEquals("Pojo", b1.getName());
		assertEquals("New address", b1.getAddress());
		assertEquals("New phone", b1.getPhone());
		adminServ.deleteBorrower(505);
		assertNull(adminServ.readBorrowerByCardNo(505));
	}

	// admin update due date
	@Test
	public void testDueDate() throws SQLException {
		AdminService adminServ = new AdminService();
		BookLoan b = new BookLoan(544, 37, 499, "2018-01-31 21:31:36", "2030-03-03 03:03:03", null);
		DateTimeFormatter f = DateTimeFormatter.ofPattern(BookLoan.getDateTimePattern());
		adminServ.updateDueDate(b.getBookId(), b.getBranchId(), b.getCardNo(), b.getDueDate().format(f));
		assertEquals(true, adminServ.readLoansByCardNo(499, false).contains(b));
		adminServ.updateDueDate(b.getBookId(), b.getBranchId(), b.getCardNo(), "2021-01-01 01:01:01");
		assertEquals(false, adminServ.readLoansByCardNo(499, false).contains(b));
	}

	// admin add/delete branch
	@Test
	public void testAdminBranch() throws SQLException {
		AdminService adminServ = new AdminService();
		Branch b = new Branch(55, "TestBranch", "123 Main St.");
		adminServ.addBranch(b.getId(), b.getName(), b.getAddress());
		assertEquals(b, adminServ.readBranchById(55));
		adminServ.deleteBranch(55);
		assertNull(adminServ.readBranchById(55));
	}

	// admin add/update/delete book
	@Test
	public void testBook() throws SQLException {
		AdminService adminServ = new AdminService();
		Author a = adminServ.readAuthorById(1);
		Book b = new Book(1005, a.getId(), 100, "Hello World", a.getName());
		List<Author> authors = new ArrayList<>();
		authors.add(a);
		List<Genre> genres = new ArrayList<>();
		Genre g = adminServ.readGenreById(22);
		genres.add(g);
		adminServ.addBook(b.getId(), b.getTitle(), authors, genres, b.getPubId(), 20, 50, false);
		assertEquals(b, adminServ.readBookById(1005));
		assertEquals(a, adminServ.readAuthorsByBookId(b.getId()).get(0));
		assertEquals(g, adminServ.readGenresByBookId(b.getId()).get(0));
		assertEquals(50, adminServ.readCopiesByIds(b.getId(), 20).getNoOfCopies());

		Author a2 = adminServ.readAuthorById(2);
		Book b2 = new Book(1005, a2.getId(), 98, "Book2", a2.getName());
		authors = new ArrayList<>();
		authors.add(a2);
		genres = new ArrayList<>();
		Genre g2 = adminServ.readGenreById(23);
		genres.add(g2);
		adminServ.updateBook(b2.getId(), b2.getTitle(), authors, genres, b2.getPubId());
		assertEquals(b2, adminServ.readBookById(1005));
		assertEquals(a2, adminServ.readAuthorsByBookId(b.getId()).get(0));
		assertEquals(g2, adminServ.readGenresByBookId(b.getId()).get(0));

		adminServ.deleteBook(b2.getId());
		assertNull(adminServ.readBookById(b2.getId()));
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
	 */
}
