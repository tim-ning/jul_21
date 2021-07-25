package com.ss.lms.presentation;

import java.util.Scanner;

/*
 * Menu object - contains a text prompt & list of action objects.
 * also handles user input choice via console.
 */
public class Menu {

	private String prompt;
	private Action[] actions;

	public Menu(String prompt, Action[] actions) {
		this.prompt = prompt;
		this.actions = actions;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	// shows menu, takes user input, & returns action
	public Action showPrompt() {
		// display menu & prompt for input
		System.out.println(prompt);
		for (int i = 0; i < actions.length; i++)
			System.out.println("\t" + (i + 1) + ") " + actions[i].getLabel());
		System.out.println();

		// read input
		String input = null;
		Scanner scan = new Scanner(System.in);
		try {
			input = scan.nextLine();
		} catch (Exception ex) {
			ex.printStackTrace();

			// if System.in malfunctions, the program has to be terminated.
			System.out.println("Program exited.");
			System.exit(0);
		}

		// developer - allow an action command to be entered directly,
		// for debugging & initial population of some tables
//		if (input.startsWith("!"))
//			return new Action("", input, 0);

		// validate input range
		int choice = -1;
		try {
			choice = Integer.parseInt(input);
		} catch (Exception ex) {
			System.out.println("Input must be an integer." + System.lineSeparator());
			return null;
		}
		if (choice > 0 && choice <= actions.length)
			return actions[choice - 1];

		System.out.println("Invalid option." + System.lineSeparator());
		return null;
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
}
