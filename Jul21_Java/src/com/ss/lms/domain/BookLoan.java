package com.ss.lms.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class BookLoan {
	private int bookId, branchId, cardNo;
	private LocalDateTime dateOut, dueDate, dateIn;
	private static final String dtPattern = "yyyy-MM-dd HH:mm:ss";

	public BookLoan(int bookId, int branchId, int cardNo, String dateOut, String dueDate, String dateIn) {
		super();
		this.bookId = bookId;
		this.branchId = branchId;
		this.cardNo = cardNo;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dtPattern);
		this.dateOut = LocalDateTime.parse(dateOut, formatter);
		this.dueDate = LocalDateTime.parse(dueDate, formatter);

		if (dateIn != null)
			this.dateIn = LocalDateTime.parse(dateIn, formatter);
	}

	public static String getDateTimePattern() {
		return dtPattern;
	}

	public int getBookId() {
		return bookId;
	}

	public int getBranchId() {
		return branchId;
	}

	public int getCardNo() {
		return cardNo;
	}

	public LocalDateTime getDateOut() {
		return dateOut;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public LocalDateTime getDateIn() {
		return dateIn;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookId, branchId, cardNo, dateIn, dateOut, dueDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookLoan other = (BookLoan) obj;
		return bookId == other.bookId && branchId == other.branchId && cardNo == other.cardNo
				&& Objects.equals(dateIn, other.dateIn) && Objects.equals(dateOut, other.dateOut)
				&& Objects.equals(dueDate, other.dueDate);
	}

}
