package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import algorithms.maseGenerators.Position;
import algorithms.search.State;


/**
 * This class is presented our maze in a 2D cross section, we fulfill the methods here in order
 * to create to the user a  and understandable cross section of the maze, in a 2 dimantional way.
 * 
 *  this class is also drawn us the start position (and if we in the right cross section its also print the goal position)
 *  and move us the character throw the maze !
 *  
 *  Actually, every time we change something in the presentation of he maze
 *  (switching cross section, moving flours, displaying solution, and even just moving the character, 
 *  this paint control from this class will run.
 * 
 * @author Omer
 */
public class Maze2D extends MazeDisplayer{

	Image startImg;
	Image goalImg;
	Image youWon;
	Image wall;
	Image solutionImg;
	//Image FloorUpImg;
	//Image FloordownImg;
	//Image FloorUpNdownImg;
	
  
	
	
	 public Maze2D(Composite parent,int style){
	        super(parent, style);
	    	// set a white background   (red, green, blue)
	    	 try {
				startImg = new Image(getDisplay(),new FileInputStream("resources/new.jpg"));
				 goalImg = new Image(getDisplay(), new FileInputStream("resources/pluto.jpg"));
				 youWon = new Image(getDisplay(),new FileInputStream("resources/won.jpg"));
				 wall = new Image(getDisplay(), new FileInputStream("resources/stars.jpg"));
				 solutionImg = new Image(getDisplay(), new FileInputStream("resources/asteroid.jpg"));
				 //FloorUpImg = new Image(getDisplay(), new FileInputStream("resources/up.gif"));
				// FloordownImg = new Image(getDisplay(), new FileInputStream("resources/down.jpg"));
				// FloorUpNdownImg = new Image(getDisplay(), new FileInputStream("resources/both.jpg"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	setBackground(new Color(null, 0, 0, 0));
	    	addPaintListener(new PaintListener() {
				
				@Override
				public void paintControl(PaintEvent e) {
					if(!victory){
					   e.gc.setForeground(new Color(null,0,0,0));
					   e.gc.setBackground(new Color(null,0,0,0));
					   
					   if(mazeData == null)
					   {
						   return;
					   }
					   
					   //int [][] mazeData = CrossSectionByY(flourCharacter) ;
					   
					   /*if(crossBy.equals("4 "))
					   {
						   int Ycharacter =characterY;
						   int Xcharacter=characterX;
						   int floorcharacter=flourCharacter;
					   }
					   
					   if(crossBy.equals("5 "))
					   {
						   int Ycharacter =characterY;
						   int Xcharacter=characterX;
						   int floorcharacter=flourCharacter;
					   }
					   
					   if(crossBy.equals("3 "))
					   {
						   int Ycharacter =characterY;
						   int Xcharacter=characterX;
						   int floorcharacter=flourCharacter;
					   }*/
					   
					   
					   
					   int width=getSize().x;
					   int height=getSize().y;

					   int w=width/mazeData[0].length;
					   int h=height/mazeData.length;
					   GC g = e.gc;

					   for(int i=0;i<mazeData.length;i++)
					      for(int j=0;j<mazeData[i].length;j++){
					    	  
					          int x=j*w;
					          int y=i*h;
					          if(mazeData[i][j]==1){
					             // e.gc.fillRectangle(x,y,w,h);
					        	  g.drawImage(wall, 0, 0, 236, 213, x, y, w, h);
					          }
					          if(solution != null)
					          {     
					        	State current = new State<Position>(new Position(flourCharacter, i, j));
					        	//Position p = new Position(flourCharacter, characterY, characterX);
					        	
					        	if(i== characterY && j==characterX)
					        	{
					        		solution.getSolution().remove(current);
					        	}
					        	//print solution
					        	if(solution.getSolution().contains(current))
					        	{
					        		 // e.gc.setBackground(new Color(null,255,0,0));
						        	 // e.gc.fillRectangle(x, y, w, h);
						        	 // e.gc.setBackground(new Color(null,0,0,0));
					        		 g.drawImage(solutionImg, 0, 0, 270, 187, x, y, w, h);
					        	}
					        	
					        	/*int end_of_loop=0;
					      		
					      		if(hintFlag == true)
					      		{
					      			
					      			end_of_loop = solution.getSolutionSize()/2;
					      		}
					      		if(hintFlag == false)
					      			end_of_loop = solution.getSolutionSize()-1;
					        	  
					        	  for(int k=1 ; k<end_of_loop ; k++)
						          {
					        		  
						        	String [] temp = solution.getSolution().get(k).getState().toString().split(",");
						  			
						  			//System.out.println(sol.getSolution().get(i));
						  			
						  			int integerY = new Integer(temp[0]).intValue();
						  			int integerZ = new Integer(temp[1]).intValue();
						  			int integerX = new Integer(temp[2]).intValue();
						  			
						  			if(flourCharacter==integerY && i==integerZ && j==integerX)
						  			{
						  				  e.gc.setBackground(new Color(null,255,0,0));
							        	  e.gc.fillRectangle(x, y, w, h);
							        	  e.gc.setBackground(new Color(null,0,0,0));
						  			}
						  			
						  			if(characterX == integerX && characterY == integerZ && flourCharacter == integerY)
						  			{
						  				solution.getSolution().remove(k);
						  				
						  				  /*e.gc.setBackground(new Color(null,255,255,255));
							        	  e.gc.fillRectangle(x, y, w, h);
							        	  e.gc.setBackground(new Color(null,0,0,0));*/
					          }	
						  			
						         
					        	  
					         //if you can go up and down
					         if(((flourCharacter < maze.getA()-1) && (maze.getCrossSectionByY(flourCharacter+1)[i][j] == 0)) && ((flourCharacter > 0) && (maze.getCrossSectionByY(flourCharacter-1)[i][j] == 0))) //mark as green if you can go up
					         {
					            /*e.gc.setBackground(new Color(null,0,255,0));
					            e.gc.fillRectangle(x, y, w, h);
					           	e.gc.setBackground(new Color(null,0,0,0));*/
					        	// g.drawImage(FloorUpNdownImg, 0, 0, 2000, 1500, x, y, w, h);	        	  
					         //////////////////// print the pictuer that you can go up and down
					          }
					          					          
					          					          
					          else
					          {		
					            //if you can go up
					            if((flourCharacter < maze.getA()-1) && (maze.getCrossSectionByY(flourCharacter+1)[i][j] == 0))
					          	 {
					            	//g.drawImage(FloorUpImg, 0, 0, 540, 540, x, y, w, h);
					                  ////////////////////////you can go up
					          	 }
					          					        	  
					          	  //if you can go down
					          	 if((flourCharacter > 0) && (maze.getCrossSectionByY(flourCharacter-1)[i][j] == 0)) 
					             {
					         	  /*e.gc.setBackground(new Color(null,255,0,0));
					          	    e.gc.fillRectangle(x, y, w, h);
					          	    e.gc.setBackground(new Color(null,0,0,0));  */
					          		//g.drawImage(FloordownImg, 0, 0, 600, 375, x, y, w, h);				        	  
					          		 ////////////////////// print the pic that you can go down
					          	  }
					           } 
				
					          
					          
					          
					          /*if(mazeData[i][j] == 2)
					          {
					        	  e.gc.setBackground(new Color(null,255,0,0));
							        	  e.gc.fillRectangle(x, y, w, h);
							        	  e.gc.setBackground(new Color(null,0,0,0));
					          }*/
					          //character
					          if(i==characterY && j == characterX)
					          {
					        	 // e.gc.setBackground(new Color(null,0,0,255));
					        	 // e.gc.fillRectangle(x, y, w, h);
					        	 // e.gc.setBackground(new Color(null,0,0,0));
					        	  g.drawImage(startImg, 0, 0, 246, 205, x, y, w, h);
					     
					        	  //g.dispose();
					        	//  startImg.dispose();
					          }
					          if(i==exitY && j==exitX && flourCharacter == flourExit)
					          {
					        	 // e.gc.setBackground(new Color(null,0,255,0));
					        	 // e.gc.fillRectangle(x, y, w, h);
					        	 // e.gc.setBackground(new Color(null,0,0,0));  
					              g.drawImage(goalImg, 0, 0, 208, 208, x, y, w, h);
					          }
	
							  if(characterX == exitX && characterY == exitY && flourCharacter == flourExit)
							  {
								g.drawImage(youWon, 0, 0, 670, 440, 0, 0, width, height);
								victory = true;
								return;
							  }
					      }
					}
			    	else{
			    		return;
			    	}
				}  
			});
	 }


	 private void moveCharacter(int x,int y){
			
			//int [][] mazeData = CrossSectionByY(flourCharacter);
			
			if(/*x>=0 && x<mazeData[0].length && y>=0 && y<mazeData.length && */mazeData[y][x]!=1)
			{
				characterX=x;
				characterY=y;
				getDisplay().syncExec(new Runnable() {
					
					@Override
					public void run() {
						redraw();
					}
				});
			}
		}
		
		/* (non-Javadoc)
		 * @see view.MazeDisplayer#moveUp()
		 */
		@Override
		public void moveUp() 
		{
			flourCharacter++;
			//int [][] mazeData = CrossSectionByY(flourCharacter);
			
			
			moveCharacter(characterX, characterY);
			
		}
		/* (non-Javadoc)
		 * @see view.MazeDisplayer#moveDown()
		 */
		@Override
		public void moveDown() 
		{
			
			flourCharacter--;
			//int [][] mazeData = CrossSectionByY(flourCharacter);
			
			moveCharacter(characterX, characterY);
		}
		/* (non-Javadoc)
		 * @see view.MazeDisplayer#moveLeft()
		 */
		@Override
		public void moveLeft() 
		{
			int x=characterX;
			int y=characterY;
			x=x-1;
			moveCharacter(x, y);
		}
		/* (non-Javadoc)
		 * @see view.MazeDisplayer#moveRight()
		 */
		@Override
		public void moveRight() {
			int x=characterX;
			int y=characterY;
			x=x+1;
			moveCharacter(x, y);
		}
		
		@Override
		public void moveBackwards() 
		{
			int x=characterX;
			int y=characterY;
			y=y+1;
			moveCharacter(x, y);
		}
		
		@Override
		public void moveForwards() 
		{
			int x=characterX;
			int y=characterY;
			y=y-1;
			moveCharacter(x, y);
		}
		
		
		
		
		@Override
		public void setCharacterPosition(int row, int col) {
			characterX=col;
			characterY=row;
			moveCharacter(col,row);
		}
	    public void setStartAndGoalPos(Position start,Position goal){
	    	this.characterX = start.getX_pos();
	    	this.characterY = start.getZ_pos();
	    	this.exitX = goal.getX_pos();
	    	this.exitY = goal.getZ_pos();
	    }

}
