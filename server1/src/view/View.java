package view;

import java.util.Observable;

import algorithms.maseGenerators.Maze3d;
import algorithms.maseGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;

public interface View 
{
	/**
	 * this start method calling to the start method the command line interface (CLI)
	 * and from there the Io loop started running
	 */
	public void start();
	
	  
	public void displaymessage(String str) throws Exception ;
	
	public void displaymessage(Maze3d maze) throws Exception ;
	
	public void displaymessage(Solution<Position> sol) throws Exception ;
	
	public void displaymessage(int [][] crossSection) throws Exception;


	public void displaymessage(Properties p) throws Exception;

	
}
