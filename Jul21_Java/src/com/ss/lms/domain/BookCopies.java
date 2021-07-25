package com.ss.lms.domain;

import java.util.Objects;

public class BookCopies {
	private int bookId, branchId, noOfCopies;

	public BookCopies(int bookId, int branchId, int noOfCopies) {
		this.bookId = bookId;
		this.branchId = branchId;
		this.noOfCopies = noOfCopies;
	}

	public int getBookId() {
		return bookId;
	}

	public int getBranchId() {
		return branchId;
	}

	public int getNoOfCopies() {
		return noOfCopies;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookId, branchId, noOfCopies);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookCopies other = (BookCopies) obj;
		return bookId == other.bookId && branchId == other.branchId && noOfCopies == other.noOfCopies;
	}

}
