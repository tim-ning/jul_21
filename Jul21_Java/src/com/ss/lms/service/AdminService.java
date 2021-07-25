package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.lms.dao.AuthorDAO;
import com.ss.lms.dao.BookCopiesDAO;
import com.ss.lms.dao.BookDAO;
import com.ss.lms.dao.BookLoanDAO;
import com.ss.lms.dao.BorrowerDAO;
import com.ss.lms.dao.BranchDAO;
import com.ss.lms.dao.GenreDAO;
import com.ss.lms.dao.PublisherDAO;
import com.ss.lms.domain.Author;
import com.ss.lms.domain.Borrower;
import com.ss.lms.domain.Genre;
import com.ss.lms.domain.Publisher;

public class AdminService extends BaseService {

	// ---- Book ----

	// add entries into 4 tables: book, book_copies, book_authors, book_genres
	public void addBook(int bookId, String title, List<Author> authors, List<Genre> genres, int pubId, int branchId,
			int copies, boolean hasCopies) throws SQLException {

		Connection conn = null;
		try {
			conn = connUtil.getConnection();

			BookDAO bDao = new BookDAO(conn);
			bDao.addBook(bookId, title, authors.get(0).getId(), pubId);

			AuthorDAO aDao = new AuthorDAO(conn);
			for (Author a : authors)
				aDao.addAuthorByBookId(a.getId(), bookId);

			GenreDAO gDao = new GenreDAO(conn);
			for (Genre g : genres)
				gDao.addGenreByBookId(g.getId(), bookId);

			BookCopiesDAO cDao = new BookCopiesDAO(conn);
			if (hasCopies)
				cDao.updateCopies(bookId, branchId, copies);
			else
				cDao.createCopies(bookId, branchId, copies);

			conn.commit();
			printSuccess("Book added");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	// update entry in table: book
	// add/delete entries in 2 tables: book_authors, book_genres
	public void updateBook(int bookId, String title, List<Author> newAuthors, List<Genre> newGenres, int pubId)
			throws SQLException {

		Connection conn = null;
		try {
			conn = connUtil.getConnection();

			BookDAO bDao = new BookDAO(conn);
			bDao.updateBook(bookId, title, newAuthors.get(0).getId(), pubId);

			AuthorDAO aDao = new AuthorDAO(conn);
			List<Author> oldAuthors = aDao.readAuthorsByBookId(bookId);
			// add new author IDs which don't overlap with existing author IDs
			for (Author a1 : newAuthors) {
				boolean overlap = false;
				for (Author a0 : oldAuthors)
					if (a1.getId() == a0.getId()) {
						overlap = true;
						break;
					}
				if (!overlap)
					aDao.addAuthorByBookId(a1.getId(), bookId);
			}
			// delete old author IDs which don't overlap w/ new author IDs
			for (Author a0 : oldAuthors) {
				boolean overlap = false;
				for (Author a1 : newAuthors)
					if (a1.getId() == a0.getId()) {
						overlap = true;
						break;
					}
				if (!overlap)
					aDao.deleteAuthorByBookId(a0.getId(), bookId);
			}

			GenreDAO gDao = new GenreDAO(conn);
			List<Genre> oldGenres = gDao.readGenresByBookId(bookId);
			// add new genre IDs which don't overlap with existing genre IDs
			for (Genre g1 : newGenres) {
				boolean overlap = false;
				for (Genre g0 : oldGenres)
					if (g1.getId() == g0.getId()) {
						overlap = true;
						break;
					}
				if (!overlap)
					gDao.addGenreByBookId(g1.getId(), bookId);
			}
			// delete old genre IDs which don't overlap w/ new genre IDs
			for (Genre g0 : oldGenres) {
				boolean overlap = false;
				for (Genre g1 : newGenres)
					if (g1.getId() == g0.getId()) {
						overlap = true;
						break;
					}
				if (!overlap)
					gDao.deleteGenreByBookId(g0.getId(), bookId);
			}

			conn.commit();
			printSuccess("Book updated");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deleteBook(int id) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO dao = new BookDAO(conn);
			dao.deleteBook(id);
			conn.commit();
			printSuccess("Book deleted");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	// ---- Book Loan ----

	public void updateDueDate(int bookId, int branchId, int cardNo, String dueDate) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookLoanDAO dao = new BookLoanDAO(conn);
			dao.updateDueDate(bookId, branchId, cardNo, dueDate);
			conn.commit();
			printSuccess("Due date updated");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	// ---- Author ----

	public void addAuthor(int id, String name) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO dao = new AuthorDAO(conn);
			dao.addAuthor(id, name);
			conn.commit();
			printSuccess("Author added");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void updateAuthor(int id, String name) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO dao = new AuthorDAO(conn);
			dao.updateAuthor(id, name);
			conn.commit();
			printSuccess("Author updated");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deleteAuthor(int id) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO dao = new AuthorDAO(conn);
			dao.deleteAuthor(id);
			conn.commit();
			printSuccess("Author deleted");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	// retrieve from table: author
	public Author readAuthorById(int id) throws SQLException {
		Connection conn = connUtil.getConnection();
		AuthorDAO dao = new AuthorDAO(conn);
		Author g = dao.readAuthorById(id);
		conn.close();
		return g;
	}

	// retrieve from table: book_authors
	public List<Author> readAuthorsByBookId(int id) throws SQLException {
		Connection conn = connUtil.getConnection();
		AuthorDAO dao = new AuthorDAO(conn);
		List<Author> list = dao.readAuthorsByBookId(id);
		conn.close();
		return list;
	}

	public List<Author> readAllAuthors() throws SQLException {
		Connection conn = connUtil.getConnection();
		AuthorDAO dao = new AuthorDAO(conn);
		List<Author> list = dao.readAllAuthors();
		conn.close();
		return list;
	}

	// ---- Genre ----

	public void addGenreByBookId(int genreId, int bookId) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			GenreDAO dao = new GenreDAO(conn);
			dao.addGenreByBookId(genreId, bookId);
			conn.commit();
			printSuccess("Genre added");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void addGenre(int id, String name) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			GenreDAO dao = new GenreDAO(conn);
			dao.addGenre(id, name);
			conn.commit();
			printSuccess("Genre added");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void updateGenre(int id, String name) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			GenreDAO dao = new GenreDAO(conn);
			dao.updateGenre(id, name);
			conn.commit();
			printSuccess("Genre updated");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deleteGenre(int id) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			GenreDAO dao = new GenreDAO(conn);
			dao.deleteGenre(id);
			conn.commit();
			printSuccess("Genre deleted");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public Genre readGenreById(int id) throws SQLException {
		Connection conn = connUtil.getConnection();
		GenreDAO dao = new GenreDAO(conn);
		Genre g = dao.readGenreById(id);
		conn.close();
		return g;
	}

	public List<Genre> readGenresByBookId(int id) throws SQLException {
		Connection conn = connUtil.getConnection();
		GenreDAO dao = new GenreDAO(conn);
		List<Genre> list = dao.readGenresByBookId(id);
		conn.close();
		return list;
	}

	public List<Genre> readAllGenres() throws SQLException {
		Connection conn = connUtil.getConnection();
		GenreDAO dao = new GenreDAO(conn);
		List<Genre> list = dao.readAllGenres();
		conn.close();
		return list;
	}

	// ---- Publisher ----

	public void addPublisher(int id, String name, String address, String phone) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			PublisherDAO dao = new PublisherDAO(conn);
			dao.addPublisher(id, name, address, phone);
			conn.commit();
			printSuccess("Publisher added");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void updatePublisher(int id, String name, String address, String phone) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			PublisherDAO dao = new PublisherDAO(conn);
			if (!name.isBlank())
				dao.updatePublisher(id, "publisherName", name);
			if (!address.isBlank())
				dao.updatePublisher(id, "publisherAddress", address);
			if (!phone.isBlank())
				dao.updatePublisher(id, "publisherPhone", phone);
			conn.commit();
			printSuccess("Publisher updated");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deletePublisher(int id) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			PublisherDAO dao = new PublisherDAO(conn);
			dao.deletePublisher(id);
			conn.commit();
			printSuccess("Publisher deleted");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public Publisher readPublisherById(int id) throws SQLException {
		Connection conn = connUtil.getConnection();
		PublisherDAO dao = new PublisherDAO(conn);
		Publisher b = dao.readPublisherById(id);
		conn.close();
		return b;
	}

	public List<Publisher> readAllPublishers() throws SQLException {
		Connection conn = connUtil.getConnection();
		PublisherDAO dao = new PublisherDAO(conn);
		List<Publisher> list = dao.readAllPublishers();
		conn.close();
		return list;
	}

	// ---- Borrower ----

	public void addBorrower(int id, String name, String address, String phone) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BorrowerDAO dao = new BorrowerDAO(conn);
			dao.addBorrower(id, name, address, phone);
			conn.commit();
			printSuccess("Borrower added");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void updateBorrower(int id, String name, String address, String phone) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BorrowerDAO dao = new BorrowerDAO(conn);
			if (!name.isBlank())
				dao.updateBorrower(id, "name", name);
			if (!address.isBlank())
				dao.updateBorrower(id, "address", address);
			if (!phone.isBlank())
				dao.updateBorrower(id, "phone", phone);
			conn.commit();
			printSuccess("Borrower updated");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deleteBorrower(int id) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BorrowerDAO dao = new BorrowerDAO(conn);
			dao.deleteBorrower(id);
			conn.commit();
			printSuccess("Borrower deleted");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public List<Borrower> readAllBorrowers() throws SQLException {
		Connection conn = connUtil.getConnection();
		BorrowerDAO dao = new BorrowerDAO(conn);
		List<Borrower> list = dao.readAllBorrowers();
		conn.close();
		return list;
	}

	// ---- Branch ----

	public void addBranch(int id, String name, String address) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BranchDAO dao = new BranchDAO(conn);
			dao.addBranch(id, name, address);
			conn.commit();
			printSuccess("Branch added");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deleteBranch(int id) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BranchDAO dao = new BranchDAO(conn);
			dao.deleteBranch(id);
			conn.commit();
			printSuccess("Branch deleted");
		} catch (SQLException e) {
			printFailed(e);
			conn.rollback();
		} finally {
			conn.close();
		}
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
	 * 
	 */
}