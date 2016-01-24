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

import model.Client;
import model.Model;
//import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
/*import presenter.Command;
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
import presenter.Presenter;
import presenter.Properties;
import presenter.SaveMaze;
import presenter.Solve;*/
import view.BasicWindow;
import view.CLI;
import view.MazeWindow;
import view.MyView;
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

	public static void main(String[] args) throws IOException 
	{
		
		/*
		PrintWriter out = null;
		
		try { 
			out = new PrintWriter(new FileWriter("2.maz"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader("2.maz"));
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}*/
		
        XMLDecoder xml = new XMLDecoder(new FileInputStream("properties.xml"));
        Properties properties = new Properties();
        properties = (Properties)xml.readObject();
		//System.out.println(properties.getGenerateAlgorithm()+properties.getSolveAlgorithm()+properties.getThreadsRunning());
		//m.saveToZipFile("mazeWithSolution.zip");
		
		Client m = null;
		try {
			m = new Client();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Presenter p = new Presenter();
		
		MazeWindow mw=  new MazeWindow("maze",1000, 800);
        p.setM(m);
        p.setV(mw);
		m.addObserver(p);
		
		mw.addObserver(p);
		mw.run();
		
		//m.saveToZipFile("omerzipfile.zip");
		
		////////////////////////////////////////////////////////////////
		
		
		/*try {
			HashMap<Maze3d, Solution<Position>> hmsp = (HashMap<Maze3d, Solution<Position>>)m.deserialize(b);
			
			System.out.println(hmsp.containsKey(m.getMOmer()));
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		////////////////////////////////////////////////////////////////
		
		//v.start();//start the project
	}

}
