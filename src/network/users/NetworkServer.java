package network.users;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import network.interfaces.ClientConnectListener;
import network.interfaces.ClientDisconnectListener;
import network.source.ClientInfo;
import network.source.ConnectThread;

public class NetworkServer extends AbstractNetworkUser{
   	static int portNumber = 1055;
    static ServerSocket serverSocket=null;     
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
	public void broadcast(Object o) throws IOException{
		for(ClientInfo c:sk.clients){
			c.send(o);
		}
	}
}
