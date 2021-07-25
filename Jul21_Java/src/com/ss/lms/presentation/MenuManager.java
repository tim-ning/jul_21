package com.ss.lms.presentation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ss.lms.controller.AdminController;
import com.ss.lms.controller.BorrowerController;
import com.ss.lms.controller.LibrarianController;
import com.ss.lms.domain.Book;
import com.ss.lms.domain.Branch;

/*
 * This class handles the main program loop.
 * Shows the menus & sends inputs to the controller layer.
 */
public class MenuManager {

	private HashMap<String, Menu> menus;
	private String currentMenuKey;
	private Menu currentMenu;
	private AdminController adminCtrl;
	private LibrarianController libCtrl;
	private BorrowerController borrCtrl;

	private final String quitPrevious = "Quit to previous";
	private final String quitCancel = "Quit to cancel operation";
	private final String endl = System.lineSeparator();

	// constructor
	public MenuManager() throws SQLException {
		adminCtrl = new AdminController(this);
		libCtrl = new LibrarianController(this);
		borrCtrl = new BorrowerController(this);

		createMenus();
		currentMenuKey = "@main";
		currentMenu = getMenu(currentMenuKey);

		mainLoop();
	}

	// main loop of the program
	private void mainLoop() throws SQLException {

		for (boolean exit = false; !exit;) {
			// show partial book for admin create/update book submenus
			if (currentMenuKey.equals("@adminB1"))
				adminCtrl.showPartialBook(true);
			else if (currentMenuKey.equals("@adminB2"))
				adminCtrl.showPartialBook(false);

			// show menu & receive input choice
			Action choice = currentMenu.showPrompt();

			// user entered an invalid option. show menu & try again.
			if (choice == null)
				continue;

			// perform an action
			if (choice.hasAction()) {
				String actionStr = choice.getAction();

				if (actionStr.equals("!exit")) // exit program
					exit = true;
				else if (actionStr.startsWith("!admin")) // all administrator actions
					adminCtrl.performAction(actionStr.substring(6), choice.getId());
				else if (actionStr.startsWith("!lib")) // all librarian actions
					libCtrl.performAction(actionStr.substring(4), choice.getId());
				else if (actionStr.startsWith("!borr")) // all borrower actions
					borrCtrl.performAction(actionStr.substring(5), choice.getId());
				else
					System.out.println("[!] Unhandled action: " + actionStr + endl);
			}

			// go to next menu
			if (choice.hasMenu()) {
				currentMenuKey = choice.getNextMenu();
				currentMenu = getMenu(currentMenuKey);
			}
		}

		// end of program
		System.out.println("Thank you for using the Smoothstack Library Management System.");
		System.out.println("Goodbye.");
	}

	// define the contents of each menu
	private void createMenus() throws SQLException {
		menus = new HashMap<String, Menu>();

		// main menu
		String mainMenuStr = "+-----------+" + endl + "| Main Menu |" + endl + "+-----------+" + endl + endl
				+ "Welcome to the Smoothstack Library Management System." + endl + endl
				+ "Which category of user are you?";

		menus.put("@main",
				new Menu(mainMenuStr,
						new Action[] { new Action("Librarian", "@lib1"), new Action("Administrator", "@admin1"),
								new Action("Borrower", "!borrLogin", 0), new Action("Exit", "!exit", 0) }));

		// librarian menus
		menus.put("@lib1", new Menu("Librarian - Welcome", new Action[] {
				new Action("Enter the branch you manage", "@lib2"), new Action(quitPrevious, "@main") }));

		menus.put("@lib2", null); // librarian branch choices

		menus.put("@lib3", new Menu("Librarian - Select an option:",
				new Action[] { new Action("Update the details of the library branch", "!libUpdateBranch", "@lib3"),
						new Action("Add/remove copies of book in the branch", "@lib4"),
						new Action(quitPrevious, "@lib2") }));

		menus.put("@lib4", null); // librarian book choices

		// administrator menus
		String[] tables = { "Books", "Authors", "Genres", "Publishers", "Library Branches", "Borrowers" };
		String crud = "Add/Update/Delete/Read ";
		menus.put("@admin1",
				new Menu("Administrator - Select operation:",
						new Action[] { new Action(crud + tables[0], "@admin2"), new Action(crud + tables[1], "@admin3"),
								new Action(crud + tables[2], "@admin4"), new Action(crud + tables[3], "@admin5"),
								new Action(crud + tables[4], "@admin6"), new Action(crud + tables[5], "@admin7"),
								new Action("Override due date for a book loan", "!adminOverrideDate", 0),
								new Action(quitPrevious, "@main") }));

		// admin submenus
		menus.put("@admin2", adminSubmenu(tables[0], "Book"));
		menus.put("@admin3", adminSubmenu(tables[1], "Author"));
		menus.put("@admin4", adminSubmenu(tables[2], "Genre"));
		menus.put("@admin5", adminSubmenu(tables[3], "Publisher"));
		menus.put("@admin6", adminSubmenu(tables[4], "Branch"));
		menus.put("@admin7", adminSubmenu(tables[5], "Borrower"));

		// admin create book
		menus.put("@adminB1", new Menu("Select input:", new Action[] { new Action("Set book ID", "!adminSetBookId", 0),
				new Action("Set title", "!adminSetTitle", 0), new Action("Add authors", "!adminSetAuthors", 0),
				new Action("Add genres", "!adminSetGenres", 0), new Action("Set publisher", "!adminSetPublisher", 0),
				new Action("Set library branch", "!adminSetBranch", 0),
				new Action("Set number of copies", "!adminSetCopies", 0),
				new Action("Submit new book entry", "!adminAddBook", 0),
				new Action(quitCancel, "!adminResetBook", "@admin2") }));

		// admin update book
		menus.put("@adminB2", new Menu("Select input:", new Action[] { new Action("Set title", "!adminSetTitle", 0),
				new Action("Add authors", "!adminSetAuthors", 0), new Action("Add genres", "!adminSetGenres", 0),
				new Action("Set publisher", "!adminSetPublisher", 0),
				new Action("Submit book update", "!adminUpdateBook", 0),
				new Action(quitCancel, "!adminResetBook", "@admin2") }));

		// borrower menus
		menus.put("@borr1",
				new Menu("Borrower - Select an option:",
						new Action[] { new Action("Check out a book", "@borr2"), new Action("Return a book", "@borr4"),
								new Action("View your current book loans", "!borrViewLoans", 0),
								new Action(quitPrevious, "@main") }));

		menus.put("@borr2", null); // borrower branch choices checkout
		menus.put("@borr3", null); // borrower book choices
		menus.put("@borr4", null); // borrower branch choices return
		menus.put("@borr5", null); // borrower book choices
	}

	// administrator CRUD submenu
	private Menu adminSubmenu(String tableName, String tableStr) {
		List<Action> actions = new ArrayList<>();

		// special case for book submenu
		if (tableStr.equals("Book")) {
			actions.add(new Action("Add " + tableStr, "!adminResetBook", "@adminB1"));
			actions.add(new Action("Update " + tableStr, "!adminSelectUpdateBook", 0));
		} else {
			actions.add(new Action("Add " + tableStr, "!adminAdd" + tableStr, 0));
			actions.add(new Action("Update " + tableStr, "!adminUpdate" + tableStr, 0));
		}
		actions.add(new Action("Delete " + tableStr, "!adminDelete" + tableStr, 0));
		actions.add(new Action("Read all " + tableName, "!adminRead" + tableStr, 0));
		actions.add(new Action(quitPrevious, "@admin1"));

		return new Menu("Administrator - " + tableName + " submenu:", actions.toArray(new Action[0]));
	}

	// generate a list of branches or books (librarian or borrower)
	private Menu generateMenu(String key) throws SQLException {
		switch (key) {
		case "@lib2":
			return loadBranchMenu("Librarian - Select branch:", "@lib3", "@lib1", "!libSelBranch");
		case "@borr2":
			return loadBranchMenu("Pick the Branch you want to check out from:", "@borr3", "@borr1", "!borrSelBranch1");
		case "@borr4":
			return loadBranchMenu("Pick the Branch you want to return to:", "@borr5", "@borr1", "!borrSelBranch2");

		case "@lib4":
			return loadBookMenu("Pick the book you want to add copies of, to your branch:", "@lib3", "@lib3",
					"!libUpdateCopies");
		case "@borr3":
			return loadBookMenu("Pick the Book you want to check out:", "@borr1", "@borr1", "!borrCheckout");
		case "@borr5":
			return loadBookMenu("Pick the Book you want to return:", "@borr1", "@borr1", "!borrReturn");

		default:
			return null;
		}
	}

	// show a list of branches
	private Menu loadBranchMenu(String prompt, String nextMenu, String previousMenu, String actionStr)
			throws SQLException {
		List<Action> actions = new ArrayList<>();
		List<Branch> branches = null;

		if (actionStr.equals("!libSelBranch")) // all branches
			branches = libCtrl.getAllBranches();
		else if (actionStr.equals("!borrSelBranch1")) // all branches
			branches = borrCtrl.getAllBranches();
		else if (actionStr.equals("!borrSelBranch2")) { // only branches where loans exist
			branches = borrCtrl.getBranchesByCardNo();

			// if no loans, just revert back to previous menu
			if (branches.size() == 0) {
				System.out.println("You do not have any books checked out at any branches." + endl);
				return menus.get(previousMenu);
			}
		}

		for (Branch b : branches)
			actions.add(new Action(b.getName(), actionStr, b.getId(), nextMenu));
		actions.add(new Action(quitPrevious, previousMenu));

		return new Menu(prompt, actions.toArray(new Action[0]));
	}

	// show a list of books with authors, depending on the menu
	private Menu loadBookMenu(String prompt, String nextMenu, String previousMenu, String actionStr)
			throws SQLException {
		List<Book> books = null;

		if (actionStr.equals("!libUpdateCopies")) // librarian - show all books
			books = libCtrl.getAllBooks();
		else if (actionStr.equals("!borrCheckout")) // borrower - show only books from a branch
			books = borrCtrl.getBorrowerCheckoutList();
		else if (actionStr.equals("!borrReturn")) // borrower - show only books currently checked out
			books = borrCtrl.getBorrowerReturnList();

		List<Action> actions = new ArrayList<>();
		for (Book b : books)
			actions.add(new Action(b.getName(), actionStr, b.getId(), nextMenu));
		actions.add(new Action(quitCancel, previousMenu));

		return new Menu(prompt, actions.toArray(new Action[0]));
	}

	// get menu by key string
	private Menu getMenu(String key) throws SQLException {
		Menu m = menus.get(key);
		if (m == null)
			return generateMenu(key);
		else
			return m;
	}

	// called from some controller functions that redirect based on success/failure
	public void setMenu(String key) throws SQLException {
		currentMenuKey = key;
		currentMenu = getMenu(currentMenuKey);
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
