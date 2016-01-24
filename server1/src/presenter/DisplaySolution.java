package presenter;


public class DisplaySolution extends AbsCommand {

	public DisplaySolution(Presenter c) {
		super(c);
		
	}

	@Override
	public void doCommand(String str) throws Exception 
	{
		String string = new String();
		
		for(int i=0 ; i<p.getM().displaySolution(str).getSolutionSize() ; i++)
		{
			string += p.getM().displaySolution(str).getSolution().get(i).getState().toString();
			string+='\n';
		}
		
		p.getV().displaymessage(string);
	}

}
