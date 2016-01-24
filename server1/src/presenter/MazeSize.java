package presenter;

/**
 * makes the right doCommand
 * 
 * calls the mazeSize in package model
 * converts the size to a string 
 * @author Omer
 *
 */
public class MazeSize extends AbsCommand {

	public MazeSize(Presenter c) {
		super(c);
		
	}

	@Override
	public void doCommand(String str) throws Exception
	{
		String s = Integer.toString(p.getM().mazeSize(str));
		
		p.getV().displaymessage(s);
	}

}
