package com.ss.jb.two;

public class Triangle implements Shape {

	private Double base, height;

	// constructor
	public Triangle(double base, double height) {
		this.base = base;
		this.height = height;
	}

	@Override
	public Double calculateArea() {
		return height * base / 2;
	}

	@Override
	public void display() {
		System.out.println("Triangle with a base of " + base + " and a height of " + height + " has an area of "
				+ calculateArea() + "." + System.lineSeparator());
	}

}
