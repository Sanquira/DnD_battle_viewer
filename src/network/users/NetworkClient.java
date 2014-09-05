package network.users;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import network.interfaces.ConnectListener;
import network.interfaces.DisconnectListener;
import network.source.MessagePacket;
import network.source.NetworkStorage;
import network.source.PacketReceiveHandler;

public class NetworkClient extends AbstractNetworkUser{
    static NetworkStorage sk=NetworkStorage.getInstance();   
    static String hostName = "localhost";
    static String nick="nick";
    static int portNumber = 1055;
    static Socket socket=null;
    static ObjectOutputStream o;
    static ObjectInputStream i;
    public static void main(String[] args) throws IOException {

	        try {
	        	socket = new Socket();
	        	socket.connect(new InetSocketAddress(hostName, portNumber), 1000);
	            i=new ObjectInputStream(socket.getInputStream());
	            o=new ObjectOutputStream(socket.getOutputStream());
	            Thread t=new Thread(new PacketReceiveHandler(i,socket));
	            t.start();
	            o.writeObject(null);
	            o.writeObject(new MessagePacket(nick, "initial"));
	        } catch (UnknownHostException e) {
	            System.err.println("Don't know about host " + hostName);
	            System.exit(1);
	        } catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection to " +
	                hostName);
	            e.printStackTrace();
	            System.exit(1);
	            socket.close();
	        }
	    }
	    public void addDisconnectListener(DisconnectListener l){
	    	sk.disconnectListeners.add(l);
	    }
	    public void addConnectListener(ConnectListener l){
	    	sk.connectListeners.add(l);
	    }
	    public NetworkClient(){
	    	socket = new Socket();
	    }
	    public void connect(String host, int port, String nick) throws IOException,UnknownHostException{
        try{	
	    	socket.connect(new InetSocketAddress(host, port), 1000);
            i=new ObjectInputStream(socket.getInputStream());
            o=new ObjectOutputStream(socket.getOutputStream());
            Thread t=new Thread(new PacketReceiveHandler(i,socket));
            t.start();
            o.writeObject(null);
            o.writeObject(new MessagePacket(nick, "initial"));
            sk.callConnectEvent(socket);
        } catch (IOException e) {
            close();
        }
	    }
	    public void close() throws IOException{
	    	socket.close();
	    }
	    public Socket getSocket(){
	    	return socket;
	    }
}


