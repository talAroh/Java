package view;

import org.eclipse.swt.layout.GridLayout;

import algorithms.maseGenerators.Maze3d;
import algorithms.maseGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;

public class ServerGUI extends BasicWindow {

	public ServerGUI(String title, int width, int height) {
		super(title, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2, false));
		

	}

	@Override
	public void displaymessage(String str) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaymessage(Maze3d maze) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaymessage(Solution<Position> sol) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaymessage(int[][] crossSection) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaymessage(Properties p) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
