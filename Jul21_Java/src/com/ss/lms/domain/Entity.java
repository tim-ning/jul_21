package com.ss.lms.domain;

import java.util.Objects;

/*
 * This is the superclass for domain objects that have a unique ID.
 * Subclasses: Book, Author, Borrower, Branch, Genre, Publisher
 * All entities are read-only, via only having getter methods & no setter methods.
 */
public abstract class Entity {

	private int id;
	private String name;
	protected final String endl = System.lineSeparator();

	// every entity has an ID & a name string
	public Entity(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	// this is how the entity appears when printed in a menu
	public String getName() {
		return name;
	}

	// this is printed when selecting an admin update operation
	public abstract void printFields();

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		return id == other.id && Objects.equals(name, other.name);
	}

}
