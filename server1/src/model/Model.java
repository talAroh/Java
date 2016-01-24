package model;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

import algorithms.maseGenerators.Maze3d;
import algorithms.maseGenerators.Position;

import algorithms.search.Solution;

/**
 * an interface contains all of the functions we wrote for the user
 * 
 * we get these functions from the pdf file of the project
 * @author Omer
 *
 */
public interface Model 
{
	/**
	 * generate a 3d  maze in the current name and gets its sizes 
	 * @param name
	 * @param y - the y size of the maze
	 * @param z - the z size of the maze
	 * @param x - the x size of the maze
	 * @param algoname - means the name of the algorithm we want to generate the maze
	 * @return - return a maze with start position , goal position and the right cells in a randomalic way
	 * @throws Exception 
	 */
	public Maze3d generate3dmaze(String name,int y,int z,int x,String algoname) throws Exception ;
	
	/**
	 * solving the maze in name "name" by the algorithm in name "algorithm" 
	 * and return a solution to the right maze in the right algorithm we wanted
	 * @param name
	 * @param algorithm
	 * @return
	 * @throws Exception 
	 */
	public Solution<Position> solve(String name, String algorithm) throws Exception;
	
	/**
	 * gets the name of the maze and return the maze in the right name (as string)
	 * @param name
	 * @return string presented the maze
	 * @throws Exception 
	 */
	public String display(String name) throws Exception;
	
	/**
	 * gets the name of the file and return the size of the file
	 * (return value is in long type)
	 * @param name
	 * @return 
	 * @throws FileNotFoundException 
	 * @throws Exception 
	 */
	public long fileSize(String name) throws Exception;
	
	/**
	 * return a 2d array presented a cross section of the maze 
	 * (using the "getCrossBy" function in the Maze3d class
	 * @param index
	 * @param name
	 * @return
	 */
	public int[][] getCrossByX(int index,String name);
	
	/**
	 * return a 2d array presented a cross section of the maze 
	 * (using the "getCrossBy" function in the Maze3d class
	 * @param index
	 * @param name
	 * @return
	 */
	public int[][] getCrossByY(int index,String name);
	
	/**
	 * return a 2d array presented a cross section of the maze 
	 * (using the "getCrossBy" function in the Maze3d class
	 * @param index
	 * @param name
	 * @return
	 */
	public int[][] getCrossByZ(int index,String name);
	
	/**
	 * gets the name of the maze and return the size of the maze in the current name in integer
	 * format : 9+y_size * z_size * x_size
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	public int mazeSize(String name) throws Exception;
	
	/**
	 * return a string presented the files and folders 
	 * that in the right path
	 * @param path
	 * @return
	 */
	public String dir(String path);
	
	/**
	 * saving the maze called "name" in the file called "fileName"
	 * the save progsess is saving with "maz" file. all of the saving files are presented a different mazes.
	 * 
	 * the file saved in "lib" library. you can read the maze back from the file using load maze function
	 * @param name
	 * @param fileName
	 */
	public void saveMaze(String name,String fileName);
	
	/**
	 * Load from the file called "fileName" a new maze that will be saved
	 * under the name "name"
	 * 
	 * We are actually converting the files from "lib" library to actuall maze3d that you can work with
	 * @param fileName
	 * @param name
	 */
	public void loadMaze(String fileName,String name);
	
	/**
	 * display the solution of the maze called "name"
	 * returns a Solution<Position> presented the solution of the current maze
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	public Solution<Position> displaySolution(String name) throws Exception;
	
	/**
	 * gets a name of an zip file and save the 
	 * maze/solution hash map (called "mazeSolution") in a compressible way 
	 * in a zip file.
	 * 
	 * this zip file will contain the maze solution hash map
	 * 
	 * @param String path - the name of the compressed zip file
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void saveToZipFile(String path) throws FileNotFoundException, IOException;
	
	/**
     * this method gets an object(any object) and converts it 
     * to an array of byte
     * 
     * @param obj - represented any object
     * @return a presentation of the object in byte array
     * @throws IOException
     */
    //public byte[] serialize(Object obj) throws IOException ;

	public boolean readFromZipFile(String name) throws FileNotFoundException, IOException, ClassNotFoundException;

	//byte[] serialize(HashMap<Maze3d, Solution<Position>> hm) throws IOException;

	public ExecutorService getExe();

	public int getGoalIndexY(String name);
	public int getStartIndexY(String name);
	
	/**
	 * this function gets a file name of an existing properties file 
	 * @param filename of an existing xml file
	 */
	public void setGametProperties(String filename);
	
	/**
	 * Exit from the Game, makes an order exit, 
	 * closes all of the working Threads, and make sure there is nothing running after we 
	 * got out of our game
	 */
	public void exit();

}
