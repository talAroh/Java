package view;


import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import algorithms.maseGenerators.Maze3d;
import algorithms.maseGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;

public class MazeWindow extends BasicWindow{

	Timer timer;
	TimerTask task;
	String var;
	MazeDisplayer mazeD;
	Properties setting;
	
	Button solv;
	Button hint;
	
	Label currentFloor;
	
	public MazeWindow(String title, int width, int height) {
		super(title, width, height);

	}
     
	
	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2, false));
		//menu
		//////////////////////////////////////////////default setting
		setting = new Properties();
		setting.setGenerateAlgorithm("Prim");
		setting.setSolveAlgorithm("bfs");
		setting.setThreadsRunning(5);
		///////////////////////////////////////////////
		Menu menu = new Menu(shell,SWT.BAR);
		Menu file = new Menu(shell,SWT.DROP_DOWN);
		Menu help = new Menu(shell,SWT.DROP_DOWN);
		MenuItem fileHeader = new MenuItem(menu,SWT.CASCADE);
		MenuItem helpHeader = new MenuItem(menu,SWT.CASCADE);
		helpHeader.setText("Help");
		helpHeader.setMenu(help);
		fileHeader.setText("File");
		fileHeader.setMenu(file);
		
		MenuItem save;
		
		save = new MenuItem(file, SWT.PUSH);
		save.setText("Save Maze");
		
		
		save.setEnabled(false);
		
		save.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String currentMazeName;
				
				currentMazeName = mazeD.name;
				var = "11 "+currentMazeName+"," +currentMazeName;
				setChanged();
				notifyObservers(var); //the file name is the same as the maze name
				var = "";
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		MenuItem load = new MenuItem(file, SWT.PUSH);
		load.setText("Load maze");
		load.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				Shell sh = new Shell(display);
				sh.setLayout(new GridLayout(2, false));
				sh.setText("load Maze");
				sh.setSize(250,150);
				
				Label nameOfTheMaze = new Label(sh, SWT.PUSH);
				nameOfTheMaze.setText("name of the maze ");
				nameOfTheMaze.setLayoutData(new GridData(SWT.TOP, SWT.LEFT, false, false, 1, 1));
				
				Text name = new Text(sh, SWT.BORDER);
				name.setLayoutData(new GridData(SWT.TOP, SWT.RIGHT, false, false, 1, 1));
				
				Button ok=  new Button(sh, SWT.COLOR_DARK_BLUE);
				ok.setText("Load !");
				ok.addSelectionListener(new SelectionListener() {
					
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						
						mazeD.solution = null;
						String userInput = name.getText();
						var = "9 "+userInput+","+userInput;
						
						sh.close();
						setChanged();
						notifyObservers(var);
						var = "";
						solv.setEnabled(true);
						hint.setEnabled(true);
						save.setEnabled(true);
						
						
												
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				sh.open();	
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		MenuItem ShowProperties = new MenuItem(file, SWT.PUSH);
		ShowProperties.setText("Show Properties");
		ShowProperties.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Shell sh = new Shell(display);
				sh.setLayout(new GridLayout(2, false));
				sh.setSize(300, 200);
				Label createAlgorithmLabel = new Label(sh, SWT.COLOR_BLUE);
				createAlgorithmLabel.setText("create Algorithm");
				Text createAlgorithmText = new Text(sh, SWT.BORDER|SWT.READ_ONLY);
				createAlgorithmText.setEnabled(false);
				createAlgorithmText.setText(setting.getGenerateAlgorithm());
				    
			    Label solveAlgorithmLabel = new Label(sh, SWT.COLOR_BLUE);
				solveAlgorithmLabel.setText("Solve Algorithm");
			    Text solveAlgorithmText = new Text(sh, SWT.BORDER|SWT.READ_ONLY);
			    solveAlgorithmText.setEnabled(false);
				solveAlgorithmText.setText(setting.getSolveAlgorithm());
				    
		        Label numOfThreadsRunningLabel = new Label(sh, SWT.COLOR_BLUE);
			    numOfThreadsRunningLabel.setText("Threads Running");
				Text numOfThreadsRunningText = new Text(sh, SWT.BORDER|SWT.READ_ONLY);
			    numOfThreadsRunningText.setEnabled(false);
				numOfThreadsRunningText.setText(setting.getThreadsRunning()+"");
				sh.open();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		MenuItem loadXMLFile = new MenuItem(file, SWT.PUSH);
		loadXMLFile.setText("open properties file");
		loadXMLFile.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{	
				FileDialog fd=new FileDialog(shell,SWT.OPEN);
				fd.setText("open");
				fd.setFilterPath("");
				String[] filterExt = { "*.xml"};
				fd.setFilterExtensions(filterExt);
				String filename = fd.open();
				setChanged();
				notifyObservers("xml "+filename);
				/*Shell sh = new Shell(display);
				sh.setLayout(new GridLayout(1, false));
				sh.setText("setting");
				sh.setSize(200, 300);
				
				XMLDecoder xml;
				try {
					xml = new XMLDecoder(new FileInputStream("properties.xml"));
					Properties properties = new Properties();
				    properties = (Properties)xml.readObject();
				    Label createAlgorithmLabel = new Label(sh, SWT.COLOR_BLUE);
				    createAlgorithmLabel.setText("create Algorithm");
				    Text createAlgorithmText = new Text(sh, SWT.BORDER);
				    createAlgorithmText.setEnabled(false);
				    createAlgorithmText.setText(setting.getGenerateAlgorithm());
				    
				    Label solveAlgorithmLabel = new Label(sh, SWT.COLOR_BLUE);
				    solveAlgorithmLabel.setText("Solve Algorithm");
				    Text solveAlgorithmText = new Text(sh, SWT.BORDER);
				    solveAlgorithmText.setEnabled(false);
				    solveAlgorithmText.setText(setting.getSolveAlgorithm());
				    
				    Label numOfThreadsRunningLabel = new Label(sh, SWT.COLOR_BLUE);
				    numOfThreadsRunningLabel.setText("Threads Running");
				    Text numOfThreadsRunningText = new Text(sh, SWT.BORDER);
				    numOfThreadsRunningText.setEnabled(false);
				    numOfThreadsRunningText.setText(setting.getThreadsRunning()+"");
				    
				    Button changeSetting = new Button(sh, SWT.PUSH);
				    changeSetting.setText("change settings");
				    changeSetting.addSelectionListener(new SelectionListener() {
						
						@Override////////////////////////////////////////////////////////////////////////////////////check errors
						public void widgetSelected(SelectionEvent arg0) {
							createAlgorithmText.setEnabled(true);
							solveAlgorithmText.setEnabled(true);
							numOfThreadsRunningText.setEnabled(true);
							changeSetting.dispose();

							Button ok = new Button(sh, SWT.PUSH);
							ok.setText("ok");
							ok.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
							ok.addSelectionListener(new SelectionListener() {
								
							@Override
							public void widgetSelected(SelectionEvent arg0) {
								if(createAlgorithmText.getText() == "Prim" || createAlgorithmText.getText() == "DFS" || createAlgorithmText.getText() == "Simple")
									setting.setGenerateAlgorithm(createAlgorithmText.getText());
			////////////////////////////////////////////////////////////////////////////////////////////////////////////add the solve algorithms
									if(solveAlgorithmText.getText() == "bfs" || solveAlgorithmText.getText() == "DFS" || solveAlgorithmText.getText() == "Simple")
									setting.setGenerateAlgorithm(solveAlgorithmText.getText());
										
									if(Integer.parseInt(numOfThreadsRunningText.getText())>0)
										setting.setThreadsRunning(Integer.parseInt(numOfThreadsRunningText.getText()));
										
									createAlgorithmText.setEnabled(false);
									solveAlgorithmText.setEnabled(false);
									numOfThreadsRunningText.setEnabled(false);
									sh.close();
								}
								
								@Override
								public void widgetDefaultSelected(SelectionEvent arg0) {
									// TODO Auto-generated method stub
									
								}
							});
						}
						
						@Override
						public void widgetDefaultSelected(SelectionEvent arg0) {
							// TODO Auto-generated method stub
							
						}
					});
				    
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			     sh.open();
			     */
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		MenuItem exit = new MenuItem(file, SWT.PUSH);
		exit.setText("Exit Game");
		exit.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				var = "Exit";
				setChanged();
				notifyObservers(var);
						
				shell.dispose();///////////////////////////////////
			}
			
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		MenuItem insructions = new MenuItem(help, SWT.PUSH);
		insructions.setText("instructions");
		shell.setMenuBar(menu);
		insructions.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				String str = "A 3d maze game !";
				
				str+='\n';
				str+= "in this game, your mision is to reach from the start position, to the goal position";
				str+='\n';
				str+='\n';
				str+="you can move the Character with the keyboard as you like, you can move flours by PageUp PageDown";
				str+='\n';
				str += "by pressing the button solv, you will see the solution to the maze";
				str+='\n';
				str+="by pressing the button hint, you will get a hint that will help you to solv the maze";
				str+='\n';
				str+="You can also save and load mazes as you like, by open 'file' Menu and pressing save maze and load maze as well.";
				str+='\n';	
				str+="You can change the properties of the game (solution algorithem, generate algorithem and even the number of Threads";
				str+='\n';
				str+='\n';
				str+="                               Have Fun ! :)";
				
				
				displaymessage(str);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		MenuItem about = new MenuItem(help, SWT.PUSH);
		about.setText("about");
		shell.setMenuBar(menu);
		
		about.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				String str;
				
				str = "This program was builted by Omer Gadot and Tal Aroh, developers form Israel, 2016";
				str+='\n';
				str+="The program was actually builted for a final JAVA course project,";
				str+='\n';
				str+="We worked and developed this program form November 2015 to Januar 2016 ! and it tooked a very hard work !";
				str+='\n';
				str+="You can contact us throw - omer.gadot1@gmail.com - email address. ";
				str+='\n';
				str += "We want to say SPECIAL THENKS for our lecturer Eli Khalastchi our best lecturer ever !!!";
				str+='\n';
				str+='\n';
				str+="                                PEACE !";
				
				displaymessage(str);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
			
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		
		Button createMazeButton = new Button(shell, SWT.PUSH);
		createMazeButton.setText("Create Maze");
		createMazeButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));		
		createMazeButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Shell sh = new Shell(display);
				sh.setLayout(new GridLayout(2, false));
				sh.setText("Dimensions");
				sh.setSize(300, 300);
			    var = "8 ";
				Label nameOfTheMaze = new Label(sh, SWT.PUSH);
				nameOfTheMaze.setText("name Of The Maze");
				nameOfTheMaze.setLayoutData(new GridData(SWT.TOP, SWT.LEFT, false, false, 1, 1));
				
				Text name = new Text(sh, SWT.BORDER);
				name.setLayoutData(new GridData(SWT.TOP, SWT.RIGHT, false, false, 1, 1));
				
				Label Length = new Label(sh, SWT.PUSH);
				Length.setText("Length");
				Length.setLayoutData(new GridData(SWT.TOP, SWT.LEFT, false, false, 1, 1));
				
				Text lText = new Text(sh, SWT.BORDER);
				lText.setLayoutData(new GridData(SWT.TOP, SWT.RIGHT, false, false, 1, 1));
				
				
				
				Label Width = new Label(sh, SWT.PUSH);
				Width.setText("Width");
				Width.setLayoutData(new GridData(SWT.TOP, SWT.LEFT, false, false, 1, 1));
				
				Text wText = new Text(sh, SWT.BORDER);
				wText.setLayoutData(new GridData(SWT.TOP, SWT.RIGHT, false, false, 1, 1));
				
				
				Label Height = new Label(sh, SWT.PUSH);
				Height.setText("Height");	
				Height.setLayoutData(new GridData(SWT.TOP, SWT.LEFT, false, false, 1, 1));
				
				
				Text hText = new Text(sh, SWT.BORDER);
				hText.setLayoutData(new GridData(SWT.TOP, SWT.RIGHT, false, false, 1, 1));
				
				
				
				Button ok=  new Button(sh, SWT.COLOR_DARK_BLUE);
				ok.setText("Generate !");
				ok.addSelectionListener(new SelectionListener() {
					
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						
						mazeD.solution = null;
						
						var += name.getText()+",";
						var += lText.getText()+",";	
						var += wText.getText()+",";
						var += hText.getText()+","+setting.getGenerateAlgorithm();
						
						sh.close();
						setChanged();
						notifyObservers(var);
						var = "";
						solv.setEnabled(true);
						hint.setEnabled(true);
						save.setEnabled(true);
						
						
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});

				sh.open();				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});	
		
		////////////////////////////////////////////////////////////////////////////////////////
		this.mazeD=new Maze2D(shell, SWT.BORDER|SWT.FILL|SWT.DOUBLE_BUFFERED);
		this.mazeD.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,5));
		///////////////////////////////////////////////////////////////////////////////////////
		
		solv = new Button(shell, SWT.PUSH);
		solv.setText("I give up, show me the solution");
		solv.setLayoutData(new GridData(SWT.TOP, SWT.TOP, false, false, 1, 1));
		
		
			solv.setEnabled(false);
		
		solv.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				mazeD.hintFlag = false;
				var = "12 ";
				var += mazeD.name;
				var +=",";
				var += setting.getSolveAlgorithm();
				setChanged();
				notifyObservers(var);
				var = "";
				solv.setEnabled(false);
				hint.setEnabled(false);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		
		hint = new Button(shell, SWT.PUSH);
		hint.setText("Help me solv this !");
		hint.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		
		hint.setEnabled(false);
		
		hint.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
						

				mazeD.hintFlag = true;
				var = "12 ";
				var += mazeD.name;
				var +=",";
				var += setting.getSolveAlgorithm();
				setChanged();
				notifyObservers(var);
				var = "";
				hint.setEnabled(false);
			    
			    
			    
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		shell.addListener(SWT.Close, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				MessageBox mb = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				mb.setMessage("Do you really want to exit? :( ");
				
				if(mb.open() == SWT.YES)
				{
					
					var = "Exit";
					setChanged();
					notifyObservers(var);
							
					shell.dispose();
				}
				
				else
				{
					System.out.println("DSDAD");
					shell.open();
				}
				
				
				
			}
		});
		
		Group sectionGroup = new Group(shell, SWT.SHADOW_OUT);
		sectionGroup.setText("Choose section");
		sectionGroup.setLayout(new GridLayout(1, true));
		
		Button sectionY = new Button(sectionGroup, SWT.RADIO | SWT.SELECTED);
		sectionY.setText("Ycross");
		sectionY.setLayoutData(new GridData(SWT.FILL,SWT.TOP,false,false,1,1));
		sectionY.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeD.crossBy.equals("5 "))
				{
					int tempFlour = mazeD.characterY ;
					int tempY = mazeD.flourCharacter;
					int tempX = mazeD.characterX;
					
					mazeD.flourCharacter = tempFlour;
					mazeD.characterY = tempY;
					mazeD.characterX = tempX;
					
					
					mazeD.crossBy = "4 ";
				}
				
				if(mazeD.crossBy.equals("3 "))
				{
					int tempFlour = mazeD.characterX ;
					int tempX = mazeD.characterY;
					int tempY = mazeD.flourCharacter;
					
					mazeD.flourCharacter = tempFlour;
					mazeD.characterY = tempY;
					mazeD.characterX = tempX;
					
					
					mazeD.crossBy = "4 ";
				}
				
				//mazeD.crossBy = "4 ";
				setChanged();
				notifyObservers(mazeD.crossBy + (mazeD.flourCharacter) + "," +mazeD.name);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button sectionZ = new Button(sectionGroup, SWT.RADIO | SWT.SELECTED);
		sectionZ.setText("Zcross");
		sectionZ.setLayoutData(new GridData(SWT.FILL,SWT.TOP,false,false,1,1));
		sectionZ.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				if(mazeD.crossBy.equals("4 "))
				{
					int tempFlour = mazeD.characterY ;
					int tempY = mazeD.flourCharacter;
					int tempX = mazeD.characterX;
					
					mazeD.flourCharacter = tempFlour;
					mazeD.characterY = tempY;
					mazeD.characterX = tempX;
					
					
					mazeD.crossBy = "5 ";
				}
				
				if(mazeD.crossBy.equals("3 "))
				{
					int tempFlour = mazeD.characterX ;
					int tempY = mazeD.characterY;
					int tempX = mazeD.flourCharacter;
					
					mazeD.flourCharacter = tempFlour;
					mazeD.characterY = tempY;
					mazeD.characterX = tempX;
					
					
					mazeD.crossBy = "5 ";
				}
				
				setChanged();
				notifyObservers(mazeD.crossBy + (mazeD.flourCharacter) + "," +mazeD.name);
				
				/*mazeD.crossBy = "5 ";	
				setChanged();
				notifyObservers(mazeD.crossBy + (mazeD.characterY) + "," +mazeD.name);*/
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button sectionX = new Button(sectionGroup, SWT.RADIO | SWT.SELECTED);
		sectionX.setText("Xcross");
		sectionX.setLayoutData(new GridData(SWT.FILL,SWT.TOP,false,false,1,1));
		
		sectionX.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeD.crossBy.equals("5 "))
				{
					int tempFlour = mazeD.characterX ;
					int tempY = mazeD.characterY;
					int tempX = mazeD.flourCharacter;
					
					mazeD.flourCharacter = tempFlour;
					mazeD.characterY = tempY;
					mazeD.characterX = tempX;
					
					
					mazeD.crossBy = "3 ";
				}
				
				if(mazeD.crossBy.equals("4 "))
				{
					int tempFlour = mazeD.characterX ;
					int tempX = mazeD.characterY;
					int tempY = mazeD.flourCharacter;
					
					mazeD.flourCharacter = tempFlour;
					mazeD.characterY = tempY;
					mazeD.characterX = tempX;
					
					
					mazeD.crossBy = "3 ";
				}
				
				setChanged();
				notifyObservers(mazeD.crossBy + (mazeD.flourCharacter) + "," +mazeD.name);
					
					/*mazeD.crossBy = "3 ";
					setChanged();
					notifyObservers(mazeD.crossBy + (mazeD.flourCharacter) + "," +mazeD.name);*/
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/////////////////////////////////fix the label/////////////////////////////////////////////////////////////////
		
		 currentFloor = new Label(shell, SWT.PUSH);
		 currentFloor.setLayoutData(new GridData(SWT.FILL, SWT.RIGHT, false, false, 1, 1));
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		mazeD.addKeyListener(new KeyListener() 
		
		{
		
		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyPressed(KeyEvent arg0)
		{
			    if(arg0.keyCode == SWT.ARROW_LEFT)
			    {
			    	try {
						mazeD.moveLeft();
					} catch (Exception e) {
						//System.out.println("SADASD");
						//displaymessage("Can't move there");
						//mazeD.characterX++;
						e.printStackTrace();
					}
			    }
				if(arg0.keyCode == SWT.ARROW_RIGHT)
				{
					try {
						mazeD.moveRight();
					} catch (Exception e) {
						//displaymessage("Can't move there");
						//System.out.println("SADDAD");
						//mazeD.characterX--;
						e.printStackTrace();
					}
				}
					
				
				if(arg0.keyCode == SWT.PAGE_UP)
				{
					
					try{
						setChanged();
						
						notifyObservers(mazeD.crossBy + (mazeD.flourCharacter+1) + "," +mazeD.name);
						
						if(mazeD.mazeData == null || mazeD.mazeData[mazeD.characterY][mazeD.characterX] == 1)
						{
							setChanged();
							notifyObservers(mazeD.crossBy + (mazeD.flourCharacter) + "," +mazeD.name);
							
							return;
						}
						
						
						mazeD.moveUp();
						currentFloor.setText("floor :"+mazeD.flourCharacter );
						
						
						
					}catch(IndexOutOfBoundsException e)
					{
						//displaymessage("Can't move there");
						mazeD.flourCharacter--;
						e.printStackTrace();
					}
					
					
				}
				
				if(arg0.keyCode == SWT.PAGE_DOWN )
				{
					try{
						setChanged();
						notifyObservers(mazeD.crossBy + (mazeD.flourCharacter-1) + "," +mazeD.name);
						if(mazeD.mazeData == null  || mazeD.mazeData[mazeD.characterY][mazeD.characterX] == 1)
						{
							setChanged();
							notifyObservers(mazeD.crossBy+ (mazeD.flourCharacter) + "," +mazeD.name);
							
							return;
						}
						
						mazeD.moveDown();
						currentFloor.setText("floor :"+mazeD.flourCharacter );
						
					}catch(IndexOutOfBoundsException e)
					{
						//displaymessage("Can't move there");
						mazeD.flourCharacter++;
						e.printStackTrace();
					}
					
					
				}
					
				if(arg0.keyCode == SWT.ARROW_UP)
				{
					try {
						mazeD.moveForwards();
					} catch (Exception e) {
						//displaymessage("Can't move there");
						//mazeD.characterY++;
						e.printStackTrace();
					}
				}
					
				if(arg0.keyCode == SWT.ARROW_DOWN)
				{
					try {
						mazeD.moveBackwards();
					} catch (Exception e) {
						//displaymessage("Can't move there");
						//mazeD.characterY--;
						e.printStackTrace();
					}
				}
				
				
				
				/*if(mazeD.characterX == mazeD.exitX && mazeD.characterY == mazeD.exitY && mazeD.flourCharacter == mazeD.flourExit)
				{
					displaymessage("Congragulations ! you won !");
				}*/
					
		}
	}
    );	
	}
	
	public void displaymessage(Solution<Position> sol) throws Exception
	{
		
		if(mazeD.hintFlag == false)
		{
			mazeD.solution = sol;
		}
		
		int loopLength = sol.getSolutionSize()/2;
		
		if(mazeD.hintFlag == true)
		{
			mazeD.solution = new Solution<Position>();
			for(int i=0 ; i<loopLength ; i++)
			{	
				mazeD.solution.addDirectionToTheSolution(sol.getSolution().get(i));
			}
		}
		
		
		/*int end_of_loop = 0;
		
		if(hintFlag == true)
			end_of_loop = sol.getSolutionSize()/2;
		if(hintFlag == false)
			end_of_loop = sol.getSolutionSize()-1;
		
		hintFlag = false;
		
		for(int i=1 ; i<end_of_loop ; i++)
		{
			
			String [] temp = sol.getSolution().get(i).getState().toString().split(",");
			
			//System.out.println(sol.getSolution().get(i));
			
			int integerY = new Integer(temp[0]).intValue();
			int integerZ = new Integer(temp[1]).intValue();
			int integerX = new Integer(temp[2]).intValue();
			
			//mazeD.flourCharacter = integerY.intValue();
			//mazeD.characterY = integerZ.intValue();
			//mazeD.characterX = integerX.intValue();
			
			mazeD.setVal(integerY, integerZ, integerX, 2);
			
			display.syncExec(new Runnable() {
				
				@Override
				public void run() 
				{
					mazeD.redraw();
				}
			});
			
		}*/
		
		display.syncExec(new Runnable() {
			
			@Override
			public void run() 
			{
				mazeD.redraw();
			}
		});
		
		//mazeD.hintFlag = false;
	}
		
	
	public void displaymessage(Maze3d maze) throws Exception
	{
		display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				
				mazeD.victory = false; //a new maze has been generated/loaded, therefore the victory is false
										//the user did'nt win yet.
				mazeD.crossBy = "4 ";
				mazeD.mazeData = maze.getCrossSectionByY(maze.getStartPos().getY_pos());
				mazeD.maze = maze;
				mazeD.setStartPos(maze.getStartPos());
				mazeD.setGoalPos(maze.getGoalPos());
				currentFloor.setText("floor : "+mazeD.flourCharacter);
				mazeD.redraw();
				
				/*mazeD.setMazeData(maze.getCrossSectionByY(maze.getStartPos().getY_pos()));
				mazeD.setStartPos(maze.getStartPos());
				mazeD.setGoalPos(maze.getGoalPos());
				mazeD.redraw();*/
				
				/*mazeD.setMaze(maze.getMazeValues());
				mazeD.setStartPos(maze.getStartPos());
				mazeD.setGoalPos(maze.getGoalPos());
				mazeD.redraw();*/
				//currentFlour.setText("floor : "+mazeD.characterY+"out of : " + mazeD.maze.length);
			}
		});
		
		
		
		
	}
		

	@Override
	public void displaymessage(String str) 
	{
		String [] temp = str.split(":");
		
		if(temp[0].equals("The maze name is "))
			mazeD.name = temp[1];
		
		
		display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				MessageBox message = new MessageBox(shell);
				message.setMessage(str);
				message.open();
			}
		});
		
	}


	@Override
	public void displaymessage(int[][] crossSection) throws Exception {
			display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				mazeD.mazeData = crossSection;
				mazeD.redraw();
			}
		});
		
		
	}


	@Override
	public void displaymessage(Properties p) throws Exception {
		setting = p;
	}


	


	
	
	
	
	
}