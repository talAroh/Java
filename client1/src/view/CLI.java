package view;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;
import java.util.Scanner;

import algorithms.maseGenerators.Maze3d;
import algorithms.maseGenerators.Position;
import algorithms.search.Solution;
//import presenter.Command;
import presenter.Properties;


/**
 * CLI = Command Line Interface
 * 
 * the command line of the user interface
 * @author Omer
 *
 */
public class CLI extends Observable implements View
{


	
	/**
	 * CTOR
	 * @param in
	 * @param out
	 * 
	 */
	public CLI()//constarctor
	{
		//super();
		//this.in = in;
		//this.out = out;
	}
	
	/**
	 * From here out project is really working
	 * 
	 * this method is running a thread that gets a all the time
	 * String from the user input, until it gets the String "exit"
	 * 
	 * each string is running the right doCommand , that running the methods in the model 
	 */
	public void start()
	{	
		new Thread(new Runnable() {
			@Override
			public void run(){
				
				  String temp = new String();// contains the command that the user want to call
				  Scanner input = new Scanner(System.in);//input form the user
				  while(!(temp = input.nextLine()).equals("exit"))
				  {
						  try {
							  setChanged();
							  notifyObservers(temp);
							  //call the function that the user want
						  } catch (Exception e) {
							
							  e.printStackTrace();
						  }

				  }
				  setChanged();
				  notifyObservers("Exit");;// close the buffer and the printer
				  input.close();
				  return;
			      }
		
		}).start();//start the thread
		
		return;
	}

	/*
	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}*/

	/*
	
	public void exit()// close the buffer and the printer
	{
		System.out.println("Bye Bye");
		
		try {
			in.close();
		} catch (IOException e) {	
			e.printStackTrace();
		}		
		out.close();
	}
	*/
	 
	public void displaymessage(String str) {
		//out.println(str);
		//out.flush();
		
		System.out.println(str);
	}

	@Override
	public void displaymessage(Maze3d maze) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaymessage(Solution<Position> sol) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaymessage(int[][] crossSection) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaymessage(Properties p) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
