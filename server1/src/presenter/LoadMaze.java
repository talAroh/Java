package presenter;

import java.util.regex.PatternSyntaxException;

/**
 * makes the right doCommand
 * 
 * load the maze using the loadMaze function in package model 
 * 
 * set a message that the maze is ready
 * @author Omer
 *
 */
public class LoadMaze extends AbsCommand {

	public LoadMaze(Presenter c) {
		super(c);
		
	}

	@Override
	public void doCommand(String str) throws Exception
	{
		String temp[] = null;
		try {
			temp = str.split(",");
		} catch (PatternSyntaxException e) {
			p.getV().displaymessage(e.getMessage());
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			p.getV().displaymessage(e.getMessage());
			e.printStackTrace();
		}
		p.getM().loadMaze(temp[0], temp[1]);
	
	}

}
