package com.ss.lms.domain;

import java.util.Objects;

/*
 * Stores a Book along with 1 Author
 */
public class Book extends Entity {
	private int pubId;
	private String title;
	private Author author;

	// author object is always created along with book object
	public Book(int bookId, int authId, int pubId, String title, String authName) {
		super(bookId, title + " by " + authName);
		this.title = title;
		this.pubId = pubId;
		author = new Author(authId, authName);
	}

	public String getTitle() {
		return title;
	}

	public Author getAuthor() {
		return author;
	}

	public int getAuthId() {
		return author.getId();
	}

	public String getAuthName() {
		return author.getName();
	}

	public int getPubId() {
		return pubId;
	}

	@Override
	public void printFields() {
		// not used
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(author, pubId, title);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(author, other.author) && pubId == other.pubId && Objects.equals(title, other.title);
	}

}
