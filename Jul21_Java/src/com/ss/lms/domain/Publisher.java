package com.ss.lms.domain;

import java.util.Objects;

public class Publisher extends Entity {
	private String publisherAddress, publisherPhone;

	public Publisher(int id, String name, String publisherAddress, String publisherPhone) {
		super(id, name);
		this.publisherAddress = publisherAddress;
		this.publisherPhone = publisherPhone;
	}

	public String getPublisherAddress() {
		return publisherAddress;
	}

	public String getPublisherPhone() {
		return publisherPhone;
	}

	@Override
	public void printFields() {
		System.out.println("You have chosen to update the Publisher:" + endl + "\tID: " + getId() + endl + "\tName: "
				+ getName() + endl + "\tAddress: " + publisherAddress + endl + "\tPhone: " + publisherPhone + endl);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(publisherAddress, publisherPhone);
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
		Publisher other = (Publisher) obj;
		return Objects.equals(publisherAddress, other.publisherAddress)
				&& Objects.equals(publisherPhone, other.publisherPhone);
	}

}
