package com.ss.lms.presentation;

/*
 * Each menu choice is an action object. This object contains:
 *     the label for the menu item, an associated action string & id, and the next menu to continue to.
 * Action strings start with '!'
 * Menu keys start with '@'
 */
public class Action {

	private String label, action, nextMenu;
	private int id;

	// perform an action (with an id), then go to next menu
	public Action(String label, String action, int id, String nextMenu) {
		this.label = label;
		this.action = action;
		this.id = id;
		this.nextMenu = nextMenu;
	}

	// perform an action (without an id), then go to next menu
	public Action(String label, String action, String nextMenu) {
		this.label = label;
		this.action = action;
		this.nextMenu = nextMenu;
	}

	// perform an action, then go back to the same menu
	public Action(String label, String action, int id) {
		this.label = label;
		this.action = action;
		this.id = id;
	}

	// go to a menu, without performing an action
	public Action(String label, String nextMenu) {
		this.label = label;
		this.nextMenu = nextMenu;
	}

	public boolean hasMenu() {
		return nextMenu != null;
	}

	public boolean hasAction() {
		return action != null;
	}

	public String getLabel() {
		return label;
	}

	public String getAction() {
		return action;
	}

	public String getNextMenu() {
		return nextMenu;
	}

	public int getId() {
		return id;
	}

}
