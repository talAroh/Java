package boot;

import java.beans.XMLDecoder;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.List;

import model.Model;
import model.MyModel;
//import presenter.ASCIIArtClientHandler;
import presenter.Command;
import presenter.Dir;
import presenter.Display;
import presenter.DisplayCrossSectionByX;
import presenter.DisplayCrossSectionByY;
import presenter.DisplayCrossSectionByZ;
import presenter.DisplaySolution;
import presenter.FileSize;
import presenter.Generate3dMaze;
import presenter.LoadMaze;
import presenter.MazeSize;
import presenter.MyClientHandler;
import presenter.MyServer;
import presenter.Presenter;
import presenter.Properties;
import presenter.SaveMaze;
import presenter.Solve;
import view.BasicWindow;
//import view.CLI;
//import view.MazeWindow;
//import view.MyView;
import view.View;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.prefs.BackingStoreException;
import java.util.zip.GZIPInputStream;

import javax.xml.crypto.dsig.spec.HMACParameterSpec;

import algorithms.maseGenerators.Maze3d;
import algorithms.maseGenerators.Position;
import algorithms.search.Solution;

public class Run {
/////////////////////////////////////////////////server
	public static void main(String[] args) throws IOException 
	{	
		MyModel m = null;
		try {
			m = new MyModel();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Presenter p = new Presenter();
        p.setM(m);
		m.addObserver(p);
			
        XMLDecoder xml = new XMLDecoder(new FileInputStream("properties.xml"));//////////change the properties file
        Properties properties = new Properties();
        properties = (Properties)xml.readObject();

		
    		System.out.println("Server Side");
    		System.out.println("type \"close the server\" to stop it");
    		MyServer server= new MyServer(5400, new MyClientHandler(p), 5);//change to properties 
    		try {
				server.start();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    		
    		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
    		
    		while(!(in.readLine()).equals("close the server"));
    		{
    		 try {
				 server.close();
			 } catch (Exception e1) {
				 e1.printStackTrace();
			 }		
    		}

	}

}
