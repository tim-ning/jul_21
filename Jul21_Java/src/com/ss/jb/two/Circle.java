package com.ss.jb.two;

public class Circle implements Shape {

	private Double radius;

	// constructor
	public Circle(double radius) {
		this.radius = radius;
	}

	@Override
	public Double calculateArea() {
		return Math.PI * radius * radius;
	}

	@Override
	public void display() {
		System.out.println("Circle with a radius of " + radius + " has an area of "
				+ String.format("%.2f", calculateArea()) + "." + System.lineSeparator());
	}
}
