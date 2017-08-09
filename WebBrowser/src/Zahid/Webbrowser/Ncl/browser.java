package Zahid.Webbrowser.Ncl;

import javax.swing.*;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Stack;
/**
 * @author Zahid Hossain
 * @Student_Number:140299797
 * @Module CSC1022
 * @Piece_of_coursework: Web Browser
 */

public class browser extends JFrame {

	// Editor Pane declaration
	private JEditorPane windowPane = new JEditorPane();

	//Text Field declaration
	private JTextField address;

	//Panel Declaration
	private JPanel addressPanel, windowPanel, navigationBtns, toolPanel,
			toolPanel2;

	//Buttons declaration
	private JButton btnBack, btnForward, refresh, openHistory, home,
			bookmarks, setHomebtn, viewBook;

	//Scroll Panel
	private JScrollPane Scroller;
	
	//JMenu Bar to store menu
	private JMenuBar menu;
	
	//JMenu to store items
	private JMenu file;
	
	//JMenu items
	private JMenuItem item1, item2;

	//Stack for back button 
	private Stack<String> urlStack = new Stack<String>();
	
	//Stack for forward button
	private Stack<String> nextUrl = new Stack<String>();
	
	

	/* *********************************************************************************
	 * Get Methods
	 */
	
	//get windowPane (JEditorPane)
	public JEditorPane getwindowPane() {
		return this.windowPane;
	}

	//get address (JTextField
	public JTextField getAddress() {

		return this.address;
	}

	//get urlStack (stack)
	public Stack<String> getUrlStack() {

		return this.urlStack;
	}

	//get nextUrl (Stack)
	public Stack<String> getNextUrl() {

		return this.nextUrl;
	}
	
	//
	
	
	

	// //Class Functionality
	// private Functionalities f;

	
	
	public browser() {
 
		super("Zahid's Browser");
		Functionalities f = new Functionalities();
		Menu m = new Menu();
		
		address = new JTextField(255);//Text Field Creation
		Font brad = new Font("Bradley Hand ITC", Font.BOLD, 15);//Font creation
		address.setFont(brad);//set font to address

		// address action listener
		address.addActionListener(
		new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//load page
				f.loadPage(event.getActionCommand());
				//put address in stack
				urlStack.push(address.getText());
				//add to history.
				f.history(); 
			}
		});
		
		//JMenu elements creation
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem item1 = new JMenuItem("ClearHistory.");
		JMenuItem item2 = new JMenuItem("Clear Bookmarks");
	
		//add JMenu to JMenuBar
		menu.add(file);
		//add JMenuItems to JMenu
		file.add(item1);
		file.add(item2);
		
		//add action listener to option
		item1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.historyClear(e.getActionCommand());
			
			}
		});
		
		//add action listener to option
		item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.favouritesClear();
			
			}
		});
			
		
		//Buttons + action listeners below:
		
		
		//set home page
		setHomebtn = new JButton("Set Home Page");
		//add action listener to button
		setHomebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.setHome();
			}

		});
		
		// Home
		ImageIcon homeImage = new ImageIcon("homeLogo.png");//set button image
		home = new JButton(homeImage);
		home.setPreferredSize(new Dimension(46, 40));//set button size
		//add action listener to button
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.homework(e.getActionCommand());
				f.history();
			}

		});

		// Reload page
		ImageIcon load = new ImageIcon("reload.png");//image to button (Image origins "commons.wikimedia.org" later edited by me.)
		refresh = new JButton(load); 
		refresh.setPreferredSize(new Dimension(41, 40));//button size
		//add action listener to button
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.reload(e.getActionCommand());
			}
		});

		// Opens history on click
		openHistory = new JButton("History");
		openHistory.setPreferredSize(new Dimension(180, 40));//button size
		//add action listener to button
		openHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.openHistory(e.getActionCommand());
			}
		});
		
		//add to bookmark on click
		bookmarks = new JButton("Bookmark it");
		bookmarks.setPreferredSize(new Dimension(120, 40));//button size
		//add action listener to button
		bookmarks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.favourites();
			}
		});
 
		
		//View Bookmark
		viewBook = new JButton("Favourites");
		//add action listener to button
		viewBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.viewBook(e.getActionCommand());
			}
		});
		
		
		// Back button
		ImageIcon back = new ImageIcon("back.png");//set button image
		btnBack = new JButton(back);
		btnBack.setPreferredSize(new Dimension(40, 20));//set button size
		//add action listener to button
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.backAction(e.getActionCommand());
			}
		});

		// Forward button
		ImageIcon next = new ImageIcon("next.png");//set button image
		btnForward = new JButton(next);
		
		btnForward.setPreferredSize(new Dimension(40, 20));//set button size
		//add action listener to button
		btnForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.nextAction(e.getActionCommand());
				;
			}

		});

		
		//Layout Setting

		// New JPanels
		addressPanel = new JPanel(new BorderLayout());
		windowPanel = new JPanel(new BorderLayout());
		navigationBtns = new JPanel(new BorderLayout());
		toolPanel = new JPanel(new BorderLayout());
		toolPanel2 = new JPanel(new BorderLayout());
		//position buttons in tool panel
		toolPanel2.add(openHistory, BorderLayout.EAST);
		toolPanel2.add(bookmarks, BorderLayout.WEST);
		toolPanel2.add(setHomebtn, BorderLayout.CENTER);
		
		//position buttons in tool panel
		toolPanel.add(refresh, BorderLayout.WEST);
		toolPanel.add(toolPanel2, BorderLayout.CENTER);
		toolPanel.add(home, BorderLayout.EAST);

		// navigation buttons.
		navigationBtns.add(btnBack, BorderLayout.WEST);
		navigationBtns.add(btnForward, BorderLayout.CENTER);
		navigationBtns.add(viewBook, BorderLayout.EAST);

		// address and navigation.
		addressPanel.add(address, BorderLayout.CENTER);
		addressPanel.add(navigationBtns, BorderLayout.WEST);
		addressPanel.add(toolPanel, BorderLayout.SOUTH);
		addressPanel.add(menu, BorderLayout.NORTH);
		
		/* window pane. */
		windowPane = new JEditorPane();
		//make window pane display html or text.
		windowPane.setContentType("text/html");
		windowPane.setEditable(false); // this makes it so that you can't edit
		// the page.
		
		//Displays home page when program is running
		try {
			address.setText(f.homepage("HomePage.txt"));
			windowPane.setPage(f.homepage("HomePage.txt"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		// hyperlink Listener
		windowPane.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent event) {
				if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					//load page
					f.loadPage(event.getURL().toString());
					//add page to history
					f.history();
					try {
						//add new page to stack
						urlStack.push(event.getURL().toString());
						//add new page url to address
						address.setText(event.getURL().toString());
						//add new page to window pane
						windowPane.setPage(event.getURL());
					} catch (IOException e) {
						windowPane.setText("Error: " + e);
					}

				}
			}

		});
		
		//scrollpanel around windowPane
		Scroller = new JScrollPane(windowPane);
		windowPanel.add(Scroller);//add scroller to the panel
		
		//Create Pane
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		//Panel positioning
		pane.add(addressPanel, BorderLayout.NORTH);
		pane.add(windowPanel, BorderLayout.CENTER);
		setSize(820, 510); //set size of window when it starts.
		setVisible(true);//make it visible
	}

}
