package presenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MyClientHandler implements ClinetHandler{

	Object result;
	
	public MyClientHandler() {
		result = null;
	}
	
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) throws IOException {
		System.out.println("MY CLIENT HANDLER");
		BufferedReader in=new BufferedReader(new InputStreamReader(inFromClient));
		ObjectOutputStream out = new ObjectOutputStream(outToClient);
		String line = null;
		
		try {
			 line = reciveFromClient(inFromClient);
		} catch (Exception e) {
			e.printStackTrace();
		};
		try{
			while(!(line=in.readLine()).equals("Exit"))
			{
				String[] temp = line.split(" ");
				System.out.println("0: "+temp[0]+"  "+"1: "+temp[1]);
                //////////check for null pointer exeption
                out.writeObject(result);
			}
			
			//out.println("good bye");
			out.flush();
		}catch(Exception e){}
		
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
	public String reciveFromClient(InputStream inFromClient)throws Exception{
		String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(inFromClient));
		try {
			line = in.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}
	
	public void sendToClient(OutputStream out,Object obj){
		
	}
}
