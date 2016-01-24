package presenter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.lang.model.element.Element;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;

/**
 * a properties class for the user.
 * this class contains a data members that presented 
 * the properties of the project (3d maze game)
 * 
 * the user can choose the properties as he wants and can change them.
 * 
 * the properties are saved in a xml file.
 *
 * @author Omer
 *
 */
public class Properties implements Serializable
{
	/**
	 * the name of the maze generator algorithm. 
	 */
	private String generateAlgorithm; 
	
	/**
	 * the name of the maze solution algorithm
	 */
    private String solveAlgorithm;
    
    /**
     * the number of threads that running in the program.
     */
    private int threadsRunning;
    
     public String getGenerateAlgorithm() {
		return generateAlgorithm;
	}


	public void setGenerateAlgorithm(String generateAlgorithm) {
		this.generateAlgorithm = generateAlgorithm;
	}


	public String getSolveAlgorithm() {
		return solveAlgorithm;
	}


	public void setSolveAlgorithm(String solveAlgorithm) {
		this.solveAlgorithm = solveAlgorithm;
	}


	public int getThreadsRunning() {
		return threadsRunning;
	}


	public void setThreadsRunning(int threadsRunning) {
		this.threadsRunning = threadsRunning;
	}
          
    public Properties() {
	}
     
    
}
