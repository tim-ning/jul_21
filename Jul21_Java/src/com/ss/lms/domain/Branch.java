package com.ss.lms.domain;

import java.util.Objects;

public class Branch extends Entity {
	private String address;

	public Branch(int id, String name, String address) {
		super(id, name);
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public void printFields() {
		System.out.println("You have chosen to update the Branch:" + endl + "\tID: " + getId() + endl + "\tName: "
				+ getName() + endl + "\tAddress: " + address + endl);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(address);
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
		Branch other = (Branch) obj;
		return Objects.equals(address, other.address);
	}

}
