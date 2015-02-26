package network.core.users;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.List;

import network.core.annotations.AnnotationChecker;
import network.core.interfaces.ClientConnectListener;
import network.core.interfaces.ClientDisconnectListener;
import network.core.source.ClientInfo;
import network.core.source.ConnectThread;

public class NetworkServer extends AbstractNetworkUser{
   	static int portNumber = 1055;
    static ServerSocket serverSocket=null;
    public NetworkServer(){
    	sk.reset();
    	super.start();
    }
    public void addClientDisconnectListener(ClientDisconnectListener l){
    	sk.clientdisconnectListeners.add(l);
    }
	public List<ClientDisconnectListener> getClientDisconnectListeners(){
		return sk.clientdisconnectListeners;
	}
    public void create(int portNumber) throws IOException{
            serverSocket = new ServerSocket(portNumber);
            createThread();
    }
    public void create(String hostname,int portNumber) throws IOException{
        serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(hostname,portNumber));
        createThread();
    }
    public void create(String hostname,int portNumber,int timeout) throws IOException{
    	create(hostname,portNumber);
    	serverSocket.setSoTimeout(timeout);
    }
    public void createThread(){
        System.out.println("Server zahájen "+serverSocket.getLocalSocketAddress());
        Thread t=new Thread(new ConnectThread(serverSocket));            
        t.start();
        t.setName("ConnectThread");
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
	public void broadcast(Object o,String header){
		for(ClientInfo c:sk.clients.values()){
			c.send(o,header);
		}
	}
    public void registerClass(Object obj){
    	try {
			new AnnotationChecker(obj,this).processClass();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
