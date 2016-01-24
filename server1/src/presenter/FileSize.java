package presenter;



/**
 * makes the right doCommand
 * 
 * calls to the fileSize function in model package 
 * converts the long number it gets to a string presented the size of the current file
 * 
 * setMessage with that string
 * @author Omer
 *
 */
public class FileSize extends AbsCommand {

	public FileSize(Presenter c) {
		super(c);
		
	}

	@Override
	public void doCommand(String str) throws Exception 
	{
		long size = p.getM().fileSize(str);
		String ans = "" + size;
		
		
		p.getV().displaymessage(ans);
	}

}
