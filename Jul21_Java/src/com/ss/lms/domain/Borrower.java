package com.ss.lms.domain;

import java.util.Objects;

public class Borrower extends Entity {
	private String address, phone;

	public Borrower(int cardNo, String name, String address, String phone) {
		super(cardNo, name);
		this.address = address;
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	@Override
	public void printFields() {
		System.out.println("You have chosen to update the Borrower:" + endl + "\tCardNo: " + getId() + endl + "\tName: "
				+ getName() + endl + "\tAddress: " + address + endl + "\tPhone: " + phone + endl);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(address, phone);
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
		Borrower other = (Borrower) obj;
		return Objects.equals(address, other.address) && Objects.equals(phone, other.phone);
	}

}
