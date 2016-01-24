package model;

public interface Model {

	public void sendDataToServer(String data) throws Exception;
    public void reciveDataFromServer() throws Exception; 
    public void closeClient() throws Exception;
}