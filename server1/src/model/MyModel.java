package model;

import java.beans.XMLDecoder;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.plaf.synth.SynthSpinnerUI;

import algorithms.demo.SearchableAdapter;
import algorithms.maseGenerators.DFSMazeGanarator;
import algorithms.maseGenerators.Maze3d;
import algorithms.maseGenerators.Maze3dGenerator;
import algorithms.maseGenerators.Position;
import algorithms.maseGenerators.PrimMazeGenerator;
import algorithms.maseGenerators.SimpleMaze3dGenerator;
import algorithms.search.Astar;
import algorithms.search.BFS;
import algorithms.search.Heuristic;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import algorithms.search.State;
import algorithms.search.StateCostComperator;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import presenter.Properties;

/**
 * a class that implements the interface Model and fulfills those functions 
 * @author Omer
 *
 */
public class MyModel extends Observable implements Model {
     
	
	/**
	 * HashMap between the name of the maze to the 
	 * maze itself.
	 * 
	 * (HashMap <String,Maze3d>).
	 */
	HashMap<String, Maze3d> hm;
	
	int size;
	Properties p;
	
	/**
	 * HashMap between the name of the maze (String)
	 * to its solution.
	 * HashMap<String,Solution<Position>>
	 * 
	 * after we solved a current maze in a current solution algorithm,
	 * this hash map will be updated and the solution of the name of the right
	 * maze will be updated.
	 */
	HashMap<String, Solution<Position>> algohashmap;
	
	//HashMap<String,File> filehashmap;
	
	/**
	 * ctor
	 * creates a new 2 hash maps
	 * @param c
	 */
	HashMap<Maze3d, Solution<Position>> mazeSolution;
	
	Maze3d m;
	
	Solution<Position> s;
	
	public Maze3d getMOmer()
	{
		return this.m;
	}
	
	ExecutorService exe;
	
	public ExecutorService getExe() {
		return exe;
	}

	public void setExe(ExecutorService exe) {
		this.exe = exe;
	}
	
	@Override
	public int getGoalIndexY(String name)
	{
		return hm.get(name).getGoalPos().getY_pos();
	}
	
	@Override
	public int getStartIndexY(String name)
	{
		return hm.get(name).getStartPos().getY_pos();
	}
	
	public MyModel(/*int numOfTreads*/) throws FileNotFoundException, ClassNotFoundException, IOException
	{
		super();
		hm = new HashMap<String,Maze3d>();//initialize the hash map
		algohashmap = new HashMap<String,Solution<Position>>();
		mazeSolution = new HashMap<Maze3d,Solution<Position>>();
        XMLDecoder xml = new XMLDecoder(new FileInputStream("properties.xml"));//default properties file
        p = new Properties();
        p = (Properties)xml.readObject();
        exe  = Executors.newFixedThreadPool(p.getThreadsRunning());
		//this.readFromZipFile("omerzipfile.zip"); /////////////////////////////////////////////////////////////
		
	}
	
	@Override
	public Maze3d generate3dmaze(String name, int y, int z, int x,String algoname) throws Exception
	{ 
		p.setGenerateAlgorithm(algoname);
		Callable<Maze3d> a = new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				
				Maze3dGenerator mg = null;
				Maze3d maze = null;
								
				try {//set the algorithm
					if(p.getGenerateAlgorithm().equals("DFS"))
						mg = new DFSMazeGanarator();
					if(p.getGenerateAlgorithm().equals("Prim")) 
						mg = new PrimMazeGenerator();
					if(p.getGenerateAlgorithm().equals("Simple"))
						mg = new SimpleMaze3dGenerator();
					maze = mg.generate(y, z, x);
				} catch (NullPointerException e) {
					
					setChanged();
					notifyObservers("Invalid algorithm name");
					e.printStackTrace();
				}
				
			   	hm.put(name, maze);//add to the mazes;
			   	//System.out.println("Ssda");
			   	
			   	
				 setChanged();
				 notifyObservers(hm.get(name));
				 
				 setChanged();
				 notifyObservers("The maze name is :" + name);
				 
				/* if(name.equals("omer"))
					 hm.get(name).printMaze();*/
			   	
				 
			    return hm.get(name);
			}
		
		 };
		 exe.submit(a);
		 
		 /*
		 int [][] arr;
		 int yIndex = hm.get(name).getStartPos().getY_pos();
		 arr = hm.get(name).getCrossSectionByY(yIndex);
		 
		 
		 
		 /*Future<Maze3d> f =*/
		 /*setChanged();
		 notifyObservers(arr.toString() +" " + hm.get(name).getStartPos().toString() +" " + hm.get(name).getGoalPos().toString());
		 
		 System.out.println(arr.toString() +" " + hm.get(name).getStartPos().toString() +" " + hm.get(name).getGoalPos().toString());
		 
		 */
		 
		 
		 
		 return hm.get(name);
	}

	@Override
	public Solution<Position> solve(String name, String algorithm)  throws Exception{
		
		p.setSolveAlgorithm(algorithm);
		
		if(algohashmap.containsKey(name))///////////////////////
		{
			setChanged();
			notifyObservers(algohashmap.get(name));
			
			return algohashmap.get(name);
		}
		
       Callable<Solution<Position>> a = new Callable<Solution<Position>>() {

			@Override
			public Solution<Position> call() throws Exception {
				Searchable<Position> serchable  = new SearchableAdapter(hm.get(name));
				
				//System.out.println("YYYYYYYYYYYYYYYY");
				//System.out.println(hm.get(name));
				//System.out.println("YYYYYYYYYYYYYYYY");
				
				State<Position> goal_state = new State<Position>(hm.get(name).getGoalPos());
				Comparator<State<Position>> comparator = new StateCostComperator<Position>();
				Searcher<Position> a = null;
				switch(p.getSolveAlgorithm()){//set the algorithm
				case "bfs"://set the algorithm to bfs
				    a = new BFS<Position>(comparator);
					break;
				case "a star air distance"://set the algorithm to a star air distance
					Heuristic<Position> heuristic1 = new MazeAirDistance<Position>(goal_state);
					a = new Astar<Position>(heuristic1,comparator);
					break;
				case "a star manhattan distance"://set the algorithm to a star manhattan distance
					Heuristic<Position> heuristic2 = new MazeManhattanDistance<Position>(goal_state);
					a = new Astar<Position>(heuristic2,comparator);
					break;
				}
				Solution<Position> solution = new Solution<Position>();//the solution of the maze
				try {
					solution = a.search(serchable);
					
				} catch (NullPointerException e) {
					
					setChanged();
				    notifyObservers("Wrong algorithm solve name");
					e.printStackTrace();
				}
				algohashmap.put(name, solution);
				mazeSolution.put(hm.get(name),solution);
				
				setChanged();
				notifyObservers(algohashmap.get(name));
				
				//notifyObservers(name); //the name of the maze
				
				
				return algohashmap.get(name);
			}
        	
		};
		/*Future<solution<position>> f = */exe.submit(a);
		
		
        return algohashmap.get(name);
	}
	@Override
	public String display(String name) throws Exception {
	    if(!hm.containsKey(name))
	    	throw new Exception("maze name does not exist");
	    
		return hm.get(name).getMaze();
	}

	@Override
	public long fileSize(String name) throws NullPointerException
	{
		File f = new File(name +".maz");
		
	
		return f.length();
	}

	@Override
	public int[][] getCrossByX(int index, String name) 
	{
		return hm.get(name).getCrossSectionByX(index);
	}

	@Override
	public int[][] getCrossByY(int index, String name) ////////////////////////////////
	{
		try{
			return hm.get(name).getCrossSectionByY(index);
		}
		catch(IndexOutOfBoundsException e){
			setChanged();
			notifyObservers("Invalid Cross Section index");
			return null;
		}
	}

	@Override
	public int[][] getCrossByZ(int index, String name) 
	{	
		return hm.get(name).getCrossSectionByZ(index);	
	}

	@Override
	public int mazeSize(String name) throws Exception
	{
	    if(!hm.containsKey(name))
	    	throw new Exception("maze name does not exist");
	   
		return (((hm.get(name).getA()*hm.get(name).getB()*hm.get(name).getC())+9)*4);  //the real size of the maze in bytes
	}

	@Override
	public String dir(String path) throws NullPointerException
	{
         File f = new File(path);
         String []str = f.list();
         String listpath = "";
         
         for(int i=0 ; i<str.length ; i++)
        	 listpath+=str[i]+'\n'; //getting one line down in order it will be more  
         							//comfortable for the user/client to read.
         return listpath;
         
	}

	@Override
	public void saveMaze(String name, String fileName) 
	{ 
		OutputStream out=null;
		File f = new File(fileName+".maz"); //saving in "maz" file type.
		
		try {
			out = new MyCompressorOutputStream(new FileOutputStream(f));
		} catch (FileNotFoundException e4) {
			
			e4.printStackTrace();
		}
		
		try {
			out.write(hm.get(name).toByteArray());
		} catch (IOException e3) {
			
			e3.printStackTrace();
		}
		try {
			out.flush();
		} catch (IOException e2) {
			
			e2.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}	
		
		setChanged();
		notifyObservers("The maze saved successfully");
	}

	@Override
	public void loadMaze(String fileName, String name) 
	{
		InputStream in = null;
		File f = new File(fileName+".maz");
		int []arr = new int[6];
		int x = 0,y = 0,z =0;
		try {
			in = new MyDecompressorInputStream(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			
			setChanged();
			notifyObservers("Maze name '" + fileName + "' does not exists");
			
			e.printStackTrace();
		}
		
		for(int i=0 ; i<6 ; i++)
			try {
				arr[i]=in.read();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		
		try {
			y = in.read();
			
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		try {
			z = in.read();
			
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		try {
			x = in.read();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		byte[] b = new byte[(x*y*z)+9];
		
		for(int j=0 ; j<6 ; j++)
			b[j] = (byte)arr[j];
		
		b[6] = (byte)y;	
		b[7] = (byte)z;	
		b[8] = (byte)x;	
		
		try {
			in.read(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		
		Maze3d loaded=null;
		try {
			loaded = new Maze3d(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		hm.put(name, loaded);
		
		//loaded.printMaze();
		
		setChanged();
		notifyObservers(loaded);
		
		 setChanged();
		 notifyObservers("The maze name is :" + name);
	}

	@Override
	public Solution<Position> displaySolution(String name) throws Exception
	{
		if(!algohashmap.containsKey(name))
			throw new Exception("algorithm name does not exist");
		return algohashmap.get(name);
	}
	
	@Override
    public void saveToZipFile(String name) throws FileNotFoundException, IOException{ 
    	/*try {
			byte[] b = serialize(mazeSolution);
			//System.out.println(b.length);
	    	GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(name));
	    	out.write(b);///////////////////////////////////////////////////////////////////////////////////////add flush
	    	out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    
		ObjectOutputStream out = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(name)));
		out.writeObject(mazeSolution);
		out.close();
    }
	
	@Override
	public boolean readFromZipFile(String name) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ObjectInputStream in = new ObjectInputStream(new GZIPInputStream(new FileInputStream(name)));
		try {
			mazeSolution = (HashMap<Maze3d, Solution<Position>>)in.readObject();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		in.close();
		return false;
		
	}

	@Override
	public void exit() {
		getExe().shutdown();
		
		try {
			saveToZipFile("omerzipfile.zip");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setChanged();
		notifyObservers("Bye Bye");
		
		return;
		
	}

	@Override
	public void setGametProperties(String filename) {
        XMLDecoder xml = null;
		try {
			xml = new XMLDecoder(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        p = (Properties)xml.readObject();
        setChanged();
        notifyObservers(p);
	}

	
    /*@Override
    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        
        return b.toByteArray();
    }*/
	
	//@Override
    /*public byte[] serialize(HashMap<Maze3d, Solution<Position>> hm) throws IOException {
        byte [] b=null;
        ArrayList<Byte> arrList = new ArrayList<Byte>();
        
       Maze3d mazeArr[] = hm.keySet().toArray(new Maze3d[hm.keySet().size()]);
       
       for (int i = 0; i < mazeArr.length; i++) 
       {
    	   b = mazeArr[i].toByteArray();
    	   for (int j = 0; j < b.length; j++) 
    	   {
    		   arrList.add(b[j]);
		   }
    	   
    	   b = hm.get(mazeArr[i]).toByteArray();
    	   
    	   for (int j = 0; j < b.length; j++) 
    	   {
    		   arrList.add(b[j]);
		   }			   
    			   
	   }
       
       
       
       for (int i = 0; i < arrList.size(); i++)
       {
    	   b[i] = arrList.get(i);
	   }
    	   
        
        return b;
	}
    
    
   
    	
    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return o.readObject();
    }
    
   /* public HashMap<Maze3d, Solution<Position>> readFromZip(String path)
    {
    	byte [] byteArr = new byte[mazeSolution.size()];
    	
    	GZIPInputStream in=null;
		try {
			in = new GZIPInputStream(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 try {
			in.read(byteArr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }*/
    
}
