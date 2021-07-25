package com.ss.lms.domain;

public class Author extends Entity {

	public Author(int id, String name) {
		super(id, name);
	}

	@Override
	public void printFields() {
		System.out.println("You have chosen to update the Author:" + endl + "\tID: " + getId() + endl + "\tName: "
				+ getName() + endl);
	}

}
