package presenter;

/**
 * interface Command
 * defining methods that are written in the sub classes
 */
public interface Command 
{
	/**
	 * a command from the user
	 * each command gets a different parameters so we 
	 * get a string presented the parameters of the user for his specific command
	 * and were splitting this string and gets the values form it etc...
	 * 
	 * @param str
	 */
	public void doCommand(String str) throws Exception;
	
	/**
	 * 
	 * @param arr - a cross section of the maze
	 * @return return a string presented the cross section
	 */
	public String display2darr(int [][] arr);
}
