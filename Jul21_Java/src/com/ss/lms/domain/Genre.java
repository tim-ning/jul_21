package com.ss.lms.domain;

public class Genre extends Entity {

	public Genre(int id, String name) {
		super(id, name);
	}

	@Override
	public void printFields() {
		System.out.println("You have chosen to update the Genre:" + endl + "\tID: " + getId() + endl + "\tName: "
				+ getName() + endl);
	}

}
