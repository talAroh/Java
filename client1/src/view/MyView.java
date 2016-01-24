package view;

import algorithms.maseGenerators.Maze3d;
import algorithms.maseGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;

/**
 * this class is actually fulfills all of our methods from the 
 * View Interface 
 * @author Omer
 *
 */
public class MyView  implements View
{
	/**
	 * Ctor, gets a cli(Command Line Interface)
	 * @param cli
	 */
	public MyView(CLI cli) 
	{	
		super();
		this.cli = cli;
	}
	public MyView(){
		
	}
	
	
	/**
	 * CLI - Command Line Interface
	 * our command line interface 
	 */
	CLI cli;




	public void start() 
	{
		cli.start();
	}

	@Override
	public void displaymessage(String str)
	{
	    if(str.equals(null))
	    	throw new NullPointerException("invalid input");
		try {
			cli.displaymessage(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	/*@Override
	public int getUserCommand()
	{
        return 0;
	}*/	
}
