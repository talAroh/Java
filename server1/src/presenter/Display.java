package presenter;

/**
 * makes the right doCommand
 * 
 * displays our maze as a 3d array of int and converts it to string
 * calls the display function in package model and uses the getMaze function in Maze3d
 * @author Omer
 *
 */
public class Display extends AbsCommand 
{

	public Display(Presenter c)
	{
		super(c);
	}

	@Override
	public void doCommand(String str) throws Exception
	{	
		String ans = p.getM().display(str);
		
		p.getV().displaymessage(ans); 	
	}

}
