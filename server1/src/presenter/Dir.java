package presenter;

/**
 * makes the right doCommand
 * 
 * gets a string presented the path
 * and call the  dir function in the model package that returns an string presented the right path
 * @author Omer
 *
 */

public class Dir extends AbsCommand {

	public Dir(Presenter c) {
		super(c);
		
	}

	@Override
	public void doCommand(String str) 
	{	
		try {
			p.getV().displaymessage(p.getM().dir(str));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
