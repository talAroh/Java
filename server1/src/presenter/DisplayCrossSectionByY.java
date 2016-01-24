package presenter;

import java.util.regex.PatternSyntaxException;

/**
 * makes the right doCommand
 * 
 * calls to the gerCrossBy in package model
 * converts the 2d array presented the answer to a string
 * @author Omer
 *
 */
public class DisplayCrossSectionByY extends AbsCommand {

	public DisplayCrossSectionByY(Presenter c) {
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
		int num = Integer.parseInt(temp[0]);
		int [][]arr = p.getM().getCrossByY(num,temp[1]);
		
		p.getV().displaymessage(arr);
		
		//p.getV().displaymessage(display2darr(arr));

	}

}
