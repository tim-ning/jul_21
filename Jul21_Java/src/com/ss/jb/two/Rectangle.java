package com.ss.jb.two;

public class Rectangle implements Shape {

	private Double width, height;

	// constructor
	public Rectangle(double width, double height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public Double calculateArea() {
		return width * height;
	}

	@Override
	public void display() {
		System.out.println("Rectangle with a width of " + width + " and a height of " + height + " has an area of "
				+ calculateArea() + "." + System.lineSeparator());
	}

}
