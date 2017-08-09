package Zahid.Webbrowser.Ncl;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Scanner;

/**
 * @author Zahid Hossain
 * @Student_Number:140299797
 * @Module CSC1022
 * @Piece_of_coursework: Web Browser
 */

public class Functionalities {



	private static browser b;

	//main method: originally it had it's own class, but it had to be moved, the program had problems
	public static void main(String args[]) {

		b = new browser();
		b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	//LoadPage, this method makes the webpages display
	public void loadPage(String a) {
		//history(a);
		try {
			b.getwindowPane().setPage(a);
			b.getAddress().setText(a);

		} catch (MalformedURLException e){

			JOptionPane.showMessageDialog(b.getwindowPane(), e,
					"URL may have been miss-typed or "	+ "it maybe not existing: " + e, 
					JOptionPane.ERROR_MESSAGE);

			//			b.getwindowPane().setText("URL may have been miss-typed or "
			//					+ "it maybe not existing: " + e);
		}catch (Exception e) {

			b.getwindowPane().setText("error " + e);
		}
	}

	//Refreshes the page.
	public void reload(String a) {
				
				try {
					//get text in the address bar
					b.getAddress().getText();
					//convert it to string
					String url = b.getAddress().getText();
					//Set address to bar
					b.getAddress().setText(url);
					//set to editor pane
					b.getwindowPane().setPage(url);
		
		
				} catch (Exception e) {
					b.getwindowPane().setText("error reloading " + e);
				}
	}



	//This methods goes back
	public void backAction(String s) {
		if(b.getUrlStack().size()>1){

		}

		//if there is nothing else in the Stack, show JOptionPane.
		else if (b.getUrlStack().isEmpty() ){


			JOptionPane.showMessageDialog(b.getwindowPane(), "Can't go back!",
					"ERROR!", JOptionPane.ERROR_MESSAGE);

			return;
		}

		try
		{
			//pushes the last part of the other stack
			b.getNextUrl().push(b.getUrlStack().peek());
			//pops the stack
			b.getUrlStack().pop();
			//converts stack peek in string
			String urlString = (String)b.getUrlStack().peek();
			//show web page currently on.
			b.getAddress().setText(urlString);
			b.getwindowPane().setPage(urlString);
		}

		catch(IOException  | EmptyStackException e){

			b.getwindowPane().setText("Error: " + e);
		}
	}

	public void nextAction(String s){
		if(b.getUrlStack().size()>1){

		}
		//if there is nothing else in the Stack, show JOptionPane.
		else if (b.getUrlStack().isEmpty() ){


			JOptionPane.showMessageDialog(b.getwindowPane(), "Can't go Forward!",
					"ERROR!", JOptionPane.ERROR_MESSAGE);

			return;
		}

		try{
			//pushes the last part of the other stack
			b.getUrlStack().push(b.getNextUrl().peek());
			//converts stack peek in string
			String urlString = (String)b.getNextUrl().peek();
			//show web page currently on.
			b.getAddress().setText(urlString);
			b.getwindowPane().setPage(urlString);
			//pops the stack
			b.getNextUrl().pop();

		}catch(IOException  | EmptyStackException e){
			//Exception message
			JOptionPane.showMessageDialog(b.getwindowPane(), e,
					"ERROR!", JOptionPane.ERROR_MESSAGE);
		}


	}


	//Method that makes home function work
	public void homework(String string){
		try{
			//set home page(get it from homepage method)
			String s = homepage("HomePage.txt");
			//set it in the window pane and address bar
			b.getwindowPane().setPage(s);
			b.getAddress().setText(s);

		}catch(Exception e){
			//
			JOptionPane.showMessageDialog(b.getwindowPane(), "Can't load homepage",
					"ERROR!", JOptionPane.ERROR_MESSAGE);
		}
		try{
			b.getUrlStack().push(b.getAddress().getText());
		}catch(Exception e){
			b.getwindowPane().setText("Error: " + e);
		}
	}


	//method to create a homepage.
	public String homepage(String m){
		StringBuffer st = null;
		try{
			//Finds the file on your pc
			FileReader home = new FileReader(m);
			//Buffered Reader is the part that does the reading.
			BufferedReader bufRead	=	new BufferedReader(home);
			String s;
			st =  new StringBuffer();
			while ((s = bufRead.readLine()) != null){
				st.append(s);
			}
			//close bufRead
			bufRead.close();

		}	
		catch(Exception e) {
			b.getwindowPane().setText("File not found " + e);
		}
		return st.toString();
	}

	//method to create history.
	public void history(){
		//Create new file in folder where project is being saved.
		File hist = new File("BrowserHistory.csv") ;
		try{
			//This is what writes in the file, create a new file writer first.
			FileWriter history = new FileWriter(hist, true);
			//gets the address in the bar then adds a new line to write the next address
			history.write(b.getAddress().getText() + '\n');

			//close the writer to stop.
			history.close();

		}catch(Exception e){
			b.getwindowPane().setText("File not found " + e);
		}

	}

	//method to open history and view it.
	public void openHistory(String f){
		try{
			//Finds the file on your pc
			FileReader readhistory = new FileReader("BrowserHistory.csv");
			//Buffered Reader is the part that does the reading.
			BufferedReader br	=	new BufferedReader(readhistory);
			String s;

			//read the line until you can't find any new lines and display them in JOptionPane
			while ((s = br.readLine()) != null){

				JOptionPane.showMessageDialog(b.getwindowPane(), s + '\n' + "Click ok to view next.",
						"History:", JOptionPane.INFORMATION_MESSAGE);
				//System.out.println(s + '\n'); //used to check that it works properly.

			}
			//close Buffered Read.
			br.close();


		}	
		catch(Exception e) {
			b.getwindowPane().setText("File not found " + e);
		}

	}

	// method to view bookmarks
	public void viewBook(String f){
		try{
			
			
			
			//Finds the file on your pc
			FileReader readBook = new FileReader("bookmarks.csv");
			//Buffered Reader is the part that does the reading.
			BufferedReader br	=	new BufferedReader(readBook);
			String s;

			//read the line until you can't find any new lines and display them in JOptionPane			
			while ((s = br.readLine()) != null){



				JOptionPane.showMessageDialog(b.getwindowPane(), s + " Click ok to view next",
						"Your bookmarks.",
						JOptionPane.INFORMATION_MESSAGE);
				//System.out.println(s + '\n'); Used to make checks

			}
			//close buffered reader.
			br.close();
		}


	
		catch(Exception e) {
			System.out.println("Exception");
			
			//b.getwindowPane().setText("File not found " + e);
		}

	}


	//method to create bookmarks
	public void favourites(){

		//Make new bookmarks.csv file
		File book = new File("bookmarks.csv") ;
		try{
			//write in file
			FileWriter favourite = new FileWriter(book, true);
			//write address in file
			favourite.write(b.getAddress().getText() + '\n');
			//close FileWriter
			favourite.close();
		}catch(Exception e){
			b.getwindowPane().setText("File not found " + e);
		}

	}

	//set Home Page method
	public void setHome(){
		//Call homePage.txt file
		File oldHome = new File("HomePage.txt");
		//delete the file
		oldHome.delete();
		//replace the file with the new one
		File setHome = new File("HomePage.txt") ;
		try{
			//write in file
			FileWriter fw = new FileWriter(setHome, true);
			//write the address of the site
			fw.write(b.getAddress().getText() + '\n');
			//close file writer
			fw.close();
			//when set home button is pressed this process will happen.
		}catch(Exception e){
			b.getwindowPane().setText("File not found " + e);
		}
	}

	//method to clear history
	public void historyClear(String string){
		//call browser history.csv in a File 
		File oldHistory = new File("BrowserHistory.csv");
		//delete file.
		oldHistory.delete();
		//re-create file.
		File newHistory = new File("BrowserHistory.csv") ;
		try{
			//write addresses using file writer
			FileWriter fw = new FileWriter(newHistory, true);
			fw.write(b.getAddress().getText() + '\n');
			//	close fw
			fw.close();
		}catch(Exception e){
			b.getwindowPane().setText("File not found " + e);
		}
	}

	//method to clear bookmarks
	public void favouritesClear(){
		//clear bookmarks
		File oldFavourites = new File("bookmarks.csv");
		oldFavourites.delete();
		//re-create the file
		File newFavourites = new File("bookmarks.csv") ;
		try{
			//re-write in the file using fw
			FileWriter fw = new FileWriter(newFavourites, true);
			fw.write(b.getAddress().getText() + '\n');
			//close fw.
			fw.close();
		}catch(Exception e){
			b.getwindowPane().setText("File not found " + e);
		}
	}


}

