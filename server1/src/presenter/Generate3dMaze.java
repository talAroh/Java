package presenter;

/**
 * makes the right doCommand
 * 
 * gets a string that tells how to generate the maze
 * format : "name" ,y_size , z_size, x_size ,"algorithm"
 * 
 * calls to the generate maze in package model
 * 
 * and set a message that the maze is ready
 * @author Omer
 *
 */
public class Generate3dMaze extends AbsCommand {

	public Generate3dMaze(Presenter c) {
		super(c);
		
	}

	@Override
	public void doCommand(String str) throws Exception 
	{
		String temp[] = str.split(",");
		int y =  Integer.parseInt(temp[1]);
		int z =  Integer.parseInt(temp[2]);
		int x =  Integer.parseInt(temp[3]);
	
		
		p.getM().generate3dmaze(temp[0], y, z, x,temp[4]);

	}

}
