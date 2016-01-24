package view;

import java.util.Observable;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import algorithms.maseGenerators.Maze3d;
import algorithms.maseGenerators.Position;
import algorithms.search.Solution;

/**
 * A basic GUI window.
 * 
 *Each GUI window contains a data member Display, and Shell, the Display is out current Display (The Screen)
 *and the Shell is a shell (like a window) that we are showing the user interface data in it.
 *
 * this class is a Generic class, it means that this class has to written in any project with GUI in that way
 * and without any change! , we can change the GUI but we will never need to change this class because its a generic 
 * and has to be in any project !
 * 
 * This class is implements "View" interface in order we will can to use the "start()" function and all of the 
 * displayMessage functions.
 * 
 *  this class is also implements Observable interface, in order we can to do a notification for different objects.
 * 
 * @author Omer
 */
public abstract class BasicWindow extends Observable implements View,Runnable{
	
	Display display;
	Shell shell;
	
 	public BasicWindow(String title, int width,int height) {
 		display=new Display();
 		shell = new Shell(display);
 		shell.setSize(width,height);
 		shell.setText(title);
	}
 	
 	abstract void initWidgets();

	@Override
	public void run() {
		initWidgets();
		shell.open();
		// main event loop
		 while(!shell.isDisposed()){ // while window isn't closed

		    // 1. read events, put then in a queue.
		    // 2. dispatch the assigned listener
			 
		    if(!display.readAndDispatch()){ 	// if the queue is empty
		       display.sleep(); 			// sleep until an event occurs 
		    }

		 } // shell is disposed

		 display.dispose(); // dispose OS components
	}
	
	
		
	@Override
	public void start()
	{
		this.run();
	}


	
	
}
