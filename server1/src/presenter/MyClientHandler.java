package presenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class MyClientHandler implements ClinetHandler{
	Presenter p;
	Object result;
	
	public MyClientHandler() {
		result = null;
	}
	
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) throws IOException {
		BufferedReader in=new BufferedReader(new InputStreamReader(inFromClient));
		ObjectOutputStream out = new ObjectOutputStream(outToClient);
		
		String line;
		try{
			while(!(line=in.readLine()).equals("Exit"))
			{
				String[] temp = line.split(" ");
                p.getHashMap().get(temp[0]).doCommand(temp[1]);
                //////////check for null pointer exeption
                out.writeObject(result);
			}
			
			//out.println("good bye");
			out.flush();
			p.getHashMap().get("0").doCommand("");
		}catch(Exception e){}
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
