package presenter;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * this function actually exit from our project 
 * in an ordered way and closes/exits any files or open threads
 */
public class Exit extends AbsCommand{

	public Exit(Presenter p) {
		super(p);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void doCommand(String str)
	{
		p.getM().exit();
	}

}
