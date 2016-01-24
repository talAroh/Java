package model;

import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Observable;

public class Client extends Observable implements Model {

	Object dataFromServer;
	Socket theServer;
	OutputStreamWriter dataToServer;
	ObjectInputStream in;
	
    public Client() throws Exception{
		System.out.println("Client Side");
		theServer=new Socket("localhost",5400);
		System.out.println("connected to server!");  
	}

	@Override
	public void sendDataToServer(String data) throws Exception{
		dataToServer=new OutputStreamWriter(theServer.getOutputStream());
		dataToServer.write(data, 0, data.length());
		reciveDataFromServer();
	}

	@Override
	public void reciveDataFromServer() throws Exception{
      in = new ObjectInputStream(theServer.getInputStream());
      dataFromServer = in.readObject();
	  setChanged();
	  notifyObservers(dataFromServer);
	}

	@Override
	public void closeClient() throws Exception{
	  theServer.close();
	  in.close();
	  dataToServer.close();
	}
}
