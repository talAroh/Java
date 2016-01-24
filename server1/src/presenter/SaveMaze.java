package presenter;

import java.util.regex.PatternSyntaxException;

/**
 * makes the right doCommand
 * 
 * saving the maze,
 * 
 * gets a string presented the file name and the maze name splited by ","
 * @author Omer
 *
 */
public class SaveMaze extends AbsCommand {

	public SaveMaze(Presenter c) {
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
		p.getM().saveMaze(temp[0], temp[1]);
	}

}
