package network.core.users;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.List;

import network.core.annotations.AnnotationChecker;
import network.core.interfaces.ClientConnectListener;
import network.core.interfaces.ClientDisconnectListener;
import network.core.source.MessagePacket;
import network.core.source.ServerSyncThread;
import network.core.source.ClientInfo;
import network.core.source.ConnectThread;
import network.core.source.NetworkStorage;

public class NetworkServer extends AbstractNetworkUser{
   	static int portNumber = 1055;
   	private ServerSyncThread check;
   	private ConnectThread connect;
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
    public void create(int portNumber,int timeout) throws IOException{
    	serverSocket = new ServerSocket(portNumber);
    	createThread(timeout);
    }
    public void create(int portNumber) throws IOException{
    	create(portNumber,getNetworkStorage().defaultserverTimeout);
    }
    public void create(String hostname,int portNumber) throws IOException{
    	create(hostname,portNumber,sk.defaultserverTimeout);
    }
    public void create(String hostname,int portNumber,int timeout) throws IOException{
        serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(hostname,portNumber));
        createThread(timeout);
    }
    public void createThread(int timeout){
        System.out.println("Server zahájen "+serverSocket.getLocalSocketAddress()+", verze jádra "+NetworkStorage.version);
        connect = new ConnectThread(serverSocket);
        check = new ServerSyncThread(timeout,this);
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
	public void broadcast(Serializable o,String header){
		for(ClientInfo c:sk.clients){
			c.send(o,header);
		}
	}
	public void rebroadcast(String sender, Serializable o,String header){
		for(ClientInfo c:getNetworkStorage().clients){
			if(!c.getNick().equals(sender)){
				c.send(sender,o,header);
			}
		}
	}
	public void rebroadcast(MessagePacket packet){
		rebroadcast(packet.getNick(),packet.getObject(),packet.getHeader());
	}
    public void registerClass(Object obj){
    	try {
			new AnnotationChecker(obj,this).processClass();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void shutdown() throws IOException{
    	connect.interrupt();
    	check.interrupt();
    	serverSocket.close();
    }
}
