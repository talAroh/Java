package presenter;

import java.util.regex.PatternSyntaxException;

/**
 * makes the right doCommand
 * 
 * solve the maze
 * 
 * gets a string presented the name of the maze and the solve algorithm splited by ","
 * @author Omer
 *
 */
public class Solve extends AbsCommand {

	public Solve(Presenter c) {
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
		p.getM().solve(temp[0], temp[1]);

	}

}
