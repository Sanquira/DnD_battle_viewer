package network.users;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import network.interfaces.ClientConnectListener;
import network.interfaces.ClientDisconnectListener;
import network.source.ConnectThread;
import network.source.NetworkStorage;

public class NetworkServer extends AbstractNetworkUser{
    NetworkStorage sk=NetworkStorage.getInstance();
   	static int portNumber = 1055;
    static ServerSocket serverSocket=null;     
    public static void main(String[] args) throws IOException {
	        
   
	        try {
	            serverSocket = new ServerSocket(portNumber);
	            System.out.println("Server zahájen "+serverSocket.getLocalSocketAddress());
	            Thread t=new Thread(new ConnectThread(serverSocket));
	            t.start();

	        } catch (IOException e) {
	            System.out.println("Exception caught when trying to listen on port "
	                + portNumber + " or listening for a connection");
	            System.out.println(e.getMessage());
	        }
	    }
    public void addClientDisconnectListener(ClientDisconnectListener l){
    	sk.clientdisconnectListeners.add(l);
    }
	public List<ClientDisconnectListener> getClientDisconnectListeners(){
		return sk.clientdisconnectListeners;
	}
    public void create(int portNumber) throws IOException{
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Server zahájen "+serverSocket.getLocalSocketAddress());
            Thread t=new Thread(new ConnectThread(serverSocket));
            t.start();
    }
    public void close() throws IOException {
    	serverSocket.close();
	}
    public ServerSocket getSocket(){
    	return serverSocket;
    }
	public List<ClientConnectListener> getConnectListeners(){
		return sk.clientconnectListeners;
	}
	public void addClientConnectListener(ClientConnectListener l){
		sk.clientconnectListeners.add(l);
	}
}
