package presenter;




/**
 * abstract command class
 * adding a data member Controller c.
 * 
 * 
 * @author Omer
 *
 */
public abstract class AbsCommand implements Command
{
	/**
	 * CTOR
	 * @param p
	 */
	public AbsCommand(Presenter p) {
		super();
		this.p = p;
	}

	@Override
	abstract public void doCommand(String str) throws Exception ;
	
	@Override
	public String display2darr(int [][] arr)
	{
		int col = arr[0].length;
		int row = arr.length;
		String str = new String();
		
		Integer [][] integer = new Integer[row][col];
				
		for(int i=0 ; i<row ; i++)
		{
			for(int j=0 ; j<col; j++)
			{
				//str += "" +arr[i][j];
				
				integer[i][j] = new Integer(arr[i][j]);
				str += integer[i][j].toString();
			}
			
			str += '\n';
		}
		
		return str;
		
	}

	Presenter p ;
	
}
